package com.ea.vaadin.crud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ea.vaadin.crud.Application;
import com.ea.vaadin.crud.entity.Customer;
import com.ea.vaadin.crud.repository.CustomerRepository;

@Configuration
public class Config {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	@Bean
	public CommandLineRunner loadData(CustomerRepository repository) {
		return (args) -> {
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));

			log.info("--------------------------------------------------------");
			log.info("Customers present in DB:");			
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("--------------------------------------------------------");
		};
	}


}
