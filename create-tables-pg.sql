create SCHEMA dryhome;

CREATE TABLE dryhome.products(
prod_id SERIAL NOT NULL PRIMARY KEY ,
prod_name varchar(50) NULL,
prod_description varchar(200) NULL
);
ALTER SEQUENCE dryhome."products_prod_id_seq" RESTART WITH 10;

CREATE TABLE dryhome.lookup(
"id" serial NOT NULL,
"used" varchar(50) NOT NULL,
"lu_id" int NOT NULL,
"Descript" varchar(50) NULL, --todo might need to be space instead of null
"order_no" int NOT NULL
);
ALTER SEQUENCE dryhome."lookup_id_seq" RESTART WITH 30;



CREATE TABLE dryhome.damp_proofers(
"ID" SERIAL NOT NULL  PRIMARY KEY,
"Company Name:" varchar(255) NULL, --todo make null
"Address 1" varchar(255) NULL,
"Address 2" varchar(255) NULL,
"Address3" varchar(255) NULL,
"Town" varchar(255) NULL,
"Post Code" varchar(13) NULL,
"Tel no" varchar(12) NULL,
"Title" varchar(255) NULL,
"First" varchar(15) NULL,
"Surname" varchar(15) NULL,
"Mobile" varchar(12) NULL,
"Products" varchar(255) NULL,
"Interested_OLD" varchar(255) NULL,
"Notes" text NULL,
"Paid" numeric(10,2) NULL,
"Interested" int NULL);
ALTER SEQUENCE dryhome."damp_proofers_ID_seq" RESTART WITH 4000;


CREATE TABLE dryhome.domestics(
"ID" SERIAL NOT NULL PRIMARY KEY,
"Address 1" varchar(255) NULL,
"Address 2" varchar(255) NULL,
"Address3" varchar(255) NULL,
"Town" varchar(255) NULL,
"Post Code" varchar(13) NULL,
"Tel no" varchar(12) NULL,
"Title" varchar(255) NULL,
"First" varchar(15) NULL,
"Surname" varchar(15) NULL,
"Mobile" varchar(12) NULL,
"Products" varchar(255) NULL,
"Interested_OLD" varchar(255) NULL,
"Notes" text NULL,
"Paid" numeric(10,2) NULL,
"lead" varchar(1) NULL,
"lead_name" varchar(255) NULL,
"Interested" int NULL,
"lead_number" varchar(50) NULL,
"lead_mob" varchar(50) NULL,
"sale" varchar(1) NULL,
"property_type" varchar(50) NULL,
"unit_pq" varchar(255) NULL,
"inst_pq" varchar(255) NULL,
"inv_date" varchar(50) NULL,
"inv_number" varchar(50) NULL);
ALTER SEQUENCE dryhome."domestics_ID_seq" RESTART WITH 3000;


CREATE TABLE dryhome.dp_orders(
"order_id" SERIAL NOT NULL PRIMARY KEY ,
"order_number" varchar(10) NOT NULL,
"company_id" int NOT NULL REFERENCES dryhome.damp_proofers,
"Date" date NOT NULL,
"Notes1" varchar(100) NULL,
"Status" int NULL,
"despatch_date" date NULL,
"invoice_date" date NULL,
"payment_date" date NULL,
"invAddressId" int NULL, --todo fk
"delAddressId" int NULL, --todo fk
"vatRate" numeric(10,2) NOT NULL,
"notes2" varchar(100) NULL,
"internal_notes" varchar(500) NULL,
"invoice_number" nchar(10) NULL,
"invContactId" int NULL, --todo fk
"delContactId" int NULL, --todo fk
"payment_status" varchar(100) NULL,
"payment_type" varchar(100) NULL,
"payment_amount" numeric(10, 2) NULL,
"placed_by" varchar(100) NULL,
"method" int NULL);
ALTER SEQUENCE dryhome."dp_orders_order_id_seq" RESTART WITH 5000;


CREATE TABLE dryhome.dp_order_items(
"id" SERIAL NOT NULL PRIMARY KEY ,
"order_id" int NOT NULL REFERENCES dryhome.dp_orders,
"Prod_id" int NOT NULL REFERENCES dryhome.products ,
"price" numeric(10,2) NULL,
"qty" int NULL   DEFAULT ((1)),
"Notes" varchar(200) NULL,
"order" int NULL,
"serial_number" varchar(500) NULL);
ALTER SEQUENCE dryhome."dp_order_items_id_seq" RESTART WITH 8000;


CREATE TABLE dryhome.dp_contacts(
id SERIAL NOT NULL,
company_id int NOT NULL REFERENCES dryhome.damp_proofers,
title varchar(50) NULL,
first varchar(50) NULL,
surname varchar(50) NULL
);
ALTER SEQUENCE dryhome."dp_contacts_id_seq" RESTART WITH 100;



CREATE TABLE dryhome.dp_addresses(
id SERIAL NOT NULL,
company_id int NOT NULL REFERENCES dryhome.damp_proofers,
company_name varchar(50) NULL,
address1 varchar(50) NULL,
address2 varchar(50) NULL,
address3 varchar(50) NULL,
town varchar(50) NULL,
post_code varchar(50) NULL
);
ALTER SEQUENCE dryhome."dp_contacts_id_seq" RESTART WITH 100;


