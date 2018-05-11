package com.ea.vaadin.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ea.vaadin.crud.entity.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByLastNameStartsWithIgnoreCase(String lastName);
}
