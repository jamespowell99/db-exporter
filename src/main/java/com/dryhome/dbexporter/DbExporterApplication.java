package com.dryhome.dbexporter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class DbExporterApplication implements CommandLineRunner{

	private final ExporterService exporterService;

	public static void main(String[] args) {
		SpringApplication.run(DbExporterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("...starting...");
		exporterService.export();

		//pg
//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/products.csv", "dryhome.products", Arrays.asList("prod_id"));
//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/lookup.csv", "dryhome.lookup", Arrays.asList("id", "lu_id", "order_no"));
//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/damp_proofers.csv", "dryhome.damp_proofers", Arrays.asList("ID", "Interested"));
//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/domestics.csv", "dryhome.domestics", Arrays.asList("ID", "Interested"));

//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/dp_orders.csv", "dryhome.dp_orders",
//				Arrays.asList("order_id", "company_id", "Status", "invAddressId", "delAddressId", "invContactId", "delContactId", "method"),
//				Arrays.asList("Date", "invoice_date", "payment_date", "despatch_date"));

//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/dp_order_items.csv", "dryhome.dp_order_items", Arrays.asList("id", "order_id", "Prod_id", "qty", "order"));
//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/dp_contacts.csv", "dryhome.dp_contacts", Arrays.asList("id", "company_id"));
//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/dp_addresses.csv", "dryhome.dp_addresses", Arrays.asList("id", "company_id"));


		//mssql
//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/products.csv", "dbo.products", Arrays.asList("prod_id"));
//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/lookup.csv", "dbo.lookup", Arrays.asList("id", "lu_id", "order_no"));
//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/damp_proofers.csv", "dbo.damp_proofers", Arrays.asList("ID", "Interested"));
//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/domestics.csv", "dbo.domestics", Arrays.asList("ID", "Interested"));

//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/dp_orders.csv", "dbo.dp_orders",
//				Arrays.asList("order_id", "company_id", "Status", "invAddressId", "delAddressId", "invContactId", "delContactId", "method"),
//				Arrays.asList("Date", "invoice_date", "payment_date", "despatch_date"));

//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/dp_order_items.csv", "dbo.dp_order_items", Arrays.asList("id", "order_id", "Prod_id", "qty", "order"));
//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/dp_contacts.csv", "dbo.dp_contacts", Arrays.asList("id", "company_id"));
//		exporterService.doImport("/Users/james/dev/dryhome/dryhome_data/dp_addresses.csv", "dbo.dp_addresses", Arrays.asList("id", "company_id"));

	}
}
