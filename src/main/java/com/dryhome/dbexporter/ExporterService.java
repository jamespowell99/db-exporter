package com.dryhome.dbexporter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExporterService {
    private final JdbcTemplate jdbcTemplate;

    public void export() {
        log.info("exporting...");

        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from schema.table");

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

    public void doImport(String file, String tableName, List<String> intColumns) {
        doImport(file, tableName, intColumns, new ArrayList<>());
    }

    public void doImport(String file, String tableName, List<String> intColumns, List<String> dateColumns) {
        try {
            BufferedReader brTest = new BufferedReader(new FileReader(file));
            String text = brTest .readLine();
            log.info("header is : " + text);
            String[] headers = text.split(",");
            log.info("headers are {}", (Object) headers);

            Reader in = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(headers)
                    .withFirstRecordAsHeader()
                    .parse(in);
            //todo check spring datasource url instead?
            if (tableName.contains("dbo.")) {
                jdbcTemplate.update("SET IDENTITY_INSERT " + tableName + " ON;");
            }

            for (CSVRecord record : records) {
                log.info("found: {}", record);
                List<String> headerList = Arrays.asList(headers);
                String insert = generateInsert(headerList, tableName);
                List<Object> valueList = headerList.stream()
                        .map(x -> getValue(intColumns, dateColumns, record, x))
                        .collect(Collectors.toList());
                jdbcTemplate.update(insert, valueList.toArray());
            }

            if (tableName.contains("dbo.")) {
                jdbcTemplate.update("SET IDENTITY_INSERT " + tableName + " OFF;");
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getValue(List<String> intColumns, List<String> dateColumns, CSVRecord record, String headerName) {
        String valueString = record.get(headerName);
        if ( intColumns.contains(headerName)) {
            return valueString.equals("") ? null : Integer.parseInt(valueString);
        } else if (dateColumns.contains(headerName)) {
            return valueString.equals("") ? null : LocalDate.parse(valueString.substring(0, 10));
        } else if (Arrays.asList("Paid", "vatRate", "payment_amount", "price").contains(headerName)) {
            //todo investigate paid type
            return valueString.equals("") ? null : new BigDecimal(valueString);
        } else {
            return valueString.equals("") ? null : valueString;
        }

    }

    private String generateInsert(List<String> headers, String tableName) {
        StringBuilder sb = new StringBuilder();
//        sb.append("insert into dryhome.");
        sb.append("insert into ");
        sb.append(tableName);
        sb.append("(");
        sb.append(String.join(",", headers.stream().map(x ->  "\"" + x + "\"").collect(Collectors.toList())));
        sb.append(") values (");
        sb.append(headers.stream().map(x->"?").collect(Collectors.joining(",")));
        sb.append(");");
        return sb.toString();
    }
}
