package com.dryhome.dbexporter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
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
            sb.append("INSERT INTO dryhomecrm.customer (id, company_name, address_1, address_2, address_3, town, post_code, title, first_name, last_name, tel, mobile, products, jhi_interested, paid, notes, jhi_type) VALUES (");
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

            Integer interested = (Integer) x.get("interested");
            if (interested == null || interested.equals(3)) {
                sb.append("null");
            } else {
                switch (interested) {
                    case 1:
                        sb.append("'INT'");
                        break;
                    case 2:
                        sb.append("'DNI'");
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
            sb.append("INSERT INTO dryhomecrm.customer (address_1, address_2, address_3, town, post_code, title, first_name, last_name, tel, mobile, sale_products, jhi_interested, paid, notes, jhi_lead, lead_name, lead_tel, lead_mob, status, enquiry_property, enquiry_unit_pq, enquiry_inst_pq, sale_invoice_date, sale_invoice_number, jhi_type) VALUES (");
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

            Integer interested = (Integer) x.get("interested");
            if (interested == null || interested.equals(3)) {
                sb.append("null");
            } else {
                switch (interested) {
                    case 1:
                        sb.append("'INT'");
                        break;
                    case 2:
                        sb.append("'DNI'");
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

    public void migrateProducts() {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from dbo.products order by prod_id");


        List<String> inserts = result.stream().map(x -> {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO dryhomecrm.product (name, description, id) VALUES (");

            sb.append(stringField(x.get("prod_name")));
            sb.append(stringField(x.get("prod_description")));

            sb.append(x.get("prod_id"));

            sb.append(");");
            return sb.toString();
        }).collect(Collectors.toList());

        writeToFile(inserts, "products.sql");
    }

    public void migrateDpOrders() {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from dbo.dp_orders order by order_id desc");


        List<String> inserts = result.stream().map(x -> {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO dryhomecrm.customer_order (id, order_number, notes_1, notes_2, internal_notes, order_date, despatch_date, invoice_date, payment_date, vat_rate, invoice_number, payment_status, payment_type, payment_amount, placed_by, method, customer_id) VALUES (");

            sb.append(x.get("order_id"));
            sb.append(",");

            sb.append(stringField(x.get("order_number")));
            sb.append(stringField(x.get("notes1")));
            sb.append(stringField(x.get("notes2")));
            String internalNotes = stringField(x.get("internal_notes"));
            if (internalNotes.length() > 250) {
                internalNotes = internalNotes.substring(0, 250) + "',";
            }
            sb.append(internalNotes);

            sb.append(timestampField(x.get("date")));
            sb.append(timestampField(x.get("despatch_date")));
            sb.append(timestampField(x.get("invoice_date")));
            sb.append(timestampField(x.get("payment_date")));


            //todo inv & del address * contact ids
            Object vatRate = x.get("vatRate");
            if (vatRate == null ) {
                sb.append("null,");
            } else {
                sb.append(vatRate);
                sb.append(",");
            }
            sb.append(stringField(x.get("invoice_number")));
            sb.append(stringField(x.get("payment_status")));
            sb.append(stringField(x.get("payment_type")));

            Object paymentAmount = x.get("payment_amount");
            if (paymentAmount == null ) {
                sb.append("null,");
            } else {
                sb.append(paymentAmount);
                sb.append(",");
            }
            sb.append(stringField(x.get("placed_by")));

            Integer method = (Integer) x.get("method");
            if (method == null) {
                sb.append("null");
            } else {
                switch (method) {
                    case 1:
                        sb.append("'PHONE'");
                        break;
                    case 2:
                        sb.append("'FAX'");
                        break;
                    case 3:
                        sb.append("'EMAIL'");
                        break;
                    case 4:
                        sb.append("'IN_PERSON'");
                        break;
                    default:
                        throw new RuntimeException("unrecognised method value: " + method);
                }
            }
            sb.append(",");

            Object companyId = x.get("company_id");
            if (companyId == null) {
                sb.append("null");
            } else {
                sb.append(companyId);
            }

            sb.append(");");
            return sb.toString();
        }).collect(Collectors.toList());

        writeToFile(inserts, "customer_orders.sql");
    }

    public void migrateDpOrderItems() {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from dbo.dp_order_items order by id");


        List<String> inserts = result.stream().map(x -> {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO dryhomecrm.order_item (id, price, quantity, notes, serial_number, product_id, customer_order_id) VALUES (");

            sb.append(x.get("id"));
            sb.append(",");
            Object price = x.get("price");
            if (price == null) {
                sb.append("0.00");
            } else {
                sb.append(price);
            }
            sb.append(",");
            Object qty = x.get("qty");
            if (qty == null) {
                sb.append("0");
            } else {
                sb.append(qty);
            }
            sb.append(",");

            sb.append(stringField(x.get("notes")));
            sb.append(stringField(x.get("serial_number")));

            sb.append(x.get("Prod_id"));
            sb.append(",");
            sb.append(x.get("order_id"));

            sb.append(");");
            return sb.toString();
        }).collect(Collectors.toList());

        writeToFile(inserts, "order_items.sql");
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

    private String timestampField(Object value) {
        if (value == null) {
            return "null,";
        } else {
            if (! (value instanceof Timestamp)) {
                throw new IllegalStateException("must be a timestamp: " + value);
            }

            return "'" + ((Timestamp) value).toString() + "',";
        }
    }



}
