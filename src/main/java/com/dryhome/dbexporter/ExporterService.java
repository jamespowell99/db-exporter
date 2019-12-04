package com.dryhome.dbexporter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExporterService {
    private final JdbcTemplate jdbcTemplate;

    public void export() {
        log.info("exporting...");

        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from payment_audit.payment_event");

        if (result.size() > 0) {
            Set<String> headers = result.get(0).keySet();

            FileWriter out = null;
            try {
                out = new FileWriter("out.csv");

                try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                        .withHeader(headers.toArray(new String[headers.size()])))) {
                    result.forEach(x -> {
                        try {
                            printer.printRecord(x.values());
                        } catch (IOException e) {
                            throw new RuntimeException("exception writing record", e);
                        }
                    });
                }
            } catch (IOException e) {
                throw new RuntimeException("exception writing file", e);
            }
        }

//        log.info("size: " + result.size());
    }
}
