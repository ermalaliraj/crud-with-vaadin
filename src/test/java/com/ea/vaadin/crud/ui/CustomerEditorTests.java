package com.ea.vaadin.crud.ui;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.then;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.ea.vaadin.crud.entity.Customer;
import com.ea.vaadin.crud.repository.CustomerRepository;
import com.ea.vaadin.crud.ui.CustomerEditor;

@RunWith(MockitoJUnitRunner.class)
public class CustomerEditorTests {

	private static final String FIRST_NAME = "Marcin";
	private static final String LAST_NAME = "Grzejszczak";

	@Mock CustomerRepository customerRepository;
	@InjectMocks CustomerEditor editor;

	@Test
	public void shouldStoreCustomerInRepoWhenEditorSaveClicked() {
		emptyCustomerWasSetToForm();

		this.editor.getFirstName().setValue(FIRST_NAME);
		this.editor.getLastName().setValue(LAST_NAME);
		this.editor.getSave().click();

		then(this.customerRepository).should().save(argThat(customerMatchesEditorFields()));
	}

	@Test
	public void shouldDeleteCustomerFromRepoWhenEditorDeleteClicked() {
		customerDataWasFilled();

		editor.getDelete().click();

		then(this.customerRepository).should().delete(argThat(customerMatchesEditorFields()));
	}

	private void emptyCustomerWasSetToForm() {
		this.editor.editCustomer(new Customer());
	}
	private void customerDataWasFilled() {
		this.editor.editCustomer(new Customer(FIRST_NAME, LAST_NAME));
	}

	private ArgumentMatcher<Customer> customerMatchesEditorFields() {
		return customer -> FIRST_NAME.equals(customer.getFirstName()) && LAST_NAME.equals(customer.getLastName());
	}

}
