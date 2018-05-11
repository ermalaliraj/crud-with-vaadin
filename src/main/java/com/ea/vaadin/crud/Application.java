package com.ea.vaadin.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	protected static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		log.info("Starting...");
		SpringApplication.run(Application.class);
		log.info("Vaadin CRUD application started on http://127.0.0.1:8080");
	}
	
}
