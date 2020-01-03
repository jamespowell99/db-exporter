package com.dryhome.dbexporter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MigrationService {
    private final JdbcTemplate jdbcTemplate;
    public void migrateDampProofers() {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from dbo.Damp_Proofers");


        List<String> inserts = result.stream().map(x -> {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO dryhomecrm.customer (id, company_name, address_1, address_2, address_3, town, post_code, title, first_name, last_name, tel, mobile, products, interested, paid, notes, jhi_type) VALUES (");
            sb.append(x.get("ID"));
            sb.append(",");

            String companyName = (String) x.get("Company Name:");
            sb.append(stringField(companyName));
            sb.append(stringField(x.get("Address 1"), "UNKNOWN"));
            sb.append(stringField(x.get("Address 2")));
            sb.append(stringField(x.get("Address3")));
            sb.append(stringField(x.get("Town"), "UNKNOWN"));
            sb.append(stringField(x.get("Post Code"), "UNKNOWN"));
            sb.append(stringField(x.get("Title")));
            sb.append(stringField(x.get("First")));
            sb.append(stringField(x.get("Surname")));
            sb.append(stringField(x.get("Tel no")));
            sb.append(stringField(x.get("Mobile")));
            sb.append(stringField(x.get("Products")));

//            todo - migrate to enum
            Integer interested = (Integer) x.get("interested");
            if (interested == null || interested.equals(3)) {
                sb.append("null");
            } else {
                switch (interested) {
                    case 1:
                        sb.append("'INT'");
                        break;
                    case 2:
                        sb.append("'D.N.I.'");
                        break;
                    default:
                        throw new RuntimeException("unrecognised interested value: " + interested);
                }
            }
            sb.append(",");

            sb.append(x.get("paid"));
            sb.append(",");

            String notes = (String) x.get("Notes");
            if (notes == null) {
                sb.append("null,");
            } else {
                sb.append("lo_from_bytea(0,'");
                sb.append(notes.replace("'", "''"));
                sb.append("'),");
            }

            sb.append("'DAMP_PROOFER'");

            sb.append(");");
//            System.out.println(sb.toString());
            return sb.toString();
        }).collect(Collectors.toList());

        writeToFile(inserts, "damp_proofers.sql");
    }

    public void migrateDomestics() {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from dbo.domestics order by ID");


        List<String> inserts = result.stream().map(x -> {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO dryhomecrm.customer (address_1, address_2, address_3, town, post_code, title, first_name, last_name, tel, mobile, sale_products, interested, paid, notes, jhi_lead, lead_name, lead_tel, lead_mob, status, enquiry_property, enquiry_unit_pq, enquiry_inst_pq, sale_invoice_date, sale_invoice_number, jhi_type) VALUES (");
            sb.append(stringField(x.get("Address 1"), "UNKNOWN"));
            sb.append(stringField(x.get("Address 2")));
            sb.append(stringField(x.get("Address3")));
            sb.append(stringField(x.get("Town"), "UNKNOWN"));
            sb.append(stringField(x.get("Post Code"), "UNKNOWN"));
            sb.append(stringField(x.get("Title")));
            sb.append(stringField(x.get("First")));
            sb.append(stringField(x.get("Surname")));
            sb.append(stringField(x.get("Tel no")));
            sb.append(stringField(x.get("Mobile")));
            sb.append(stringField(x.get("Products")));

//            todo - migrate to enum
            Integer interested = (Integer) x.get("interested");
            if (interested == null || interested.equals(3)) {
                sb.append("null");
            } else {
                switch (interested) {
                    case 1:
                        sb.append("'INT'");
                        break;
                    case 2:
                        sb.append("'D.N.I.'");
                        break;
                    default:
                        throw new RuntimeException("unrecognised interested value: " + interested);
                }
            }
            sb.append(",");

            sb.append(x.get("paid"));
            sb.append(",");

            String notes = (String) x.get("Notes");
            if (notes == null) {
                sb.append("null,");
            } else {
                sb.append("lo_from_bytea(0,'");
                sb.append(notes.replace("'", "''"));
                sb.append("'),");
            }

            String lead = (String) x.get("lead");
            if (lead == null || lead.equals("5")) {
                sb.append("null");
            } else {
                switch (lead) {
                    case "1":
                        sb.append("'WEBSITE'");
                        break;
                    case "2":
                        sb.append("'YELLOW_PAGES'");
                        break;
                    case "3":
                        sb.append("'DAMP_PROOFER'");
                        break;
                    case "4":
                        sb.append("'OTHER'");
                        break;
                    default:
                        throw new RuntimeException("unrecognised lead value: " + lead);
                }
            }
            sb.append(",");

            sb.append(stringField(x.get("lead_name")));
            sb.append(stringField(x.get("lead_number")));
            sb.append(stringField(x.get("lead_mob")));

            String sale = (String) x.get("sale");
            if (sale == null || sale.equals("1")) {
                sb.append("null");
            } else {
                switch (sale) {
                    case "2":
                        sb.append("'ENQUIRY'");
                        break;
                    case "3":
                        sb.append("'SALE'");
                        break;
                    default:
                        throw new RuntimeException("unrecognised sale value: " + sale);
                }
            }
            sb.append(",");

            sb.append(stringField(x.get("property_type")));
            sb.append(stringField(x.get("unit_pq")));
            sb.append(stringField(x.get("inst_pq")));
            sb.append(stringField(x.get("inv_date")));
            sb.append(stringField(x.get("inv_number")));

            sb.append("'DOMESTIC'");

            sb.append(");");
//            System.out.println(sb.toString());
            return sb.toString();
        }).collect(Collectors.toList());

        writeToFile(inserts, "domestics.sql");
    }

    private void writeToFile(List<String> inserts, String filename) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filename);

            for (String str : inserts) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("unable to write file");
        }
    }

    private String stringField(Object value) {
       return stringField(value, null);
    }

    private String stringField(Object value, String defaultValue) {
        if (value == null) {
            if (defaultValue == null) {
                return "null,";
            }
            else {
                return "'" + defaultValue + "',";
            }
        } else {
            if (! (value instanceof  String )) {
                throw new IllegalStateException("must be a string: " + value);
            }

            return "'" + ((String) value).replace("'", "''") + "',";
        }
    }
}
