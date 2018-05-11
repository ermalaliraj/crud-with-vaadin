package com.ea.vaadin.crud.ui;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ea.vaadin.crud.Application;
import com.ea.vaadin.crud.entity.Customer;
import com.ea.vaadin.crud.repository.CustomerRepository;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.VaadinRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class VaadinUITests {

	@Autowired
	CustomerRepository repository;
	VaadinRequest vaadinRequest = Mockito.mock(VaadinRequest.class);
	CustomerEditor editor;
	VaadinUI vaadinUI;

	@Before
	public void setup() {
		this.editor = new CustomerEditor(this.repository);
		this.vaadinUI = new VaadinUI(this.repository, editor);
	}

	@Test
	public void shouldInitializeTheGridWithCustomerRepositoryData() {
		int customerCount = (int) this.repository.count();

		vaadinUI.init(this.vaadinRequest);

		then(vaadinUI.grid.getColumns()).hasSize(3);
		then(getCustomersInGrid()).hasSize(customerCount);
	}

	@SuppressWarnings("unchecked")
	private List<Customer> getCustomersInGrid() {
		ListDataProvider<Customer> ldp = (ListDataProvider<Customer>) vaadinUI.grid.getDataProvider();
		return new ArrayList<>(ldp.getItems());
	}

	@Test
	public void shouldFillOutTheGridWithNewData() {
		int initialCustomerCount = (int) this.repository.count();
		this.vaadinUI.init(this.vaadinRequest);
		customerDataWasFilled(editor, "Marcin", "Grzejszczak");

		this.editor.getSave().click();

		then(getCustomersInGrid()).hasSize(initialCustomerCount + 1);

		then(getCustomersInGrid().get(getCustomersInGrid().size() - 1)).extracting("firstName", "lastName").containsExactly("Marcin", "Grzejszczak");

	}

	@Test
	public void shouldFilterOutTheGridWithTheProvidedLastName() {
		this.vaadinUI.init(this.vaadinRequest);
		this.repository.save(new Customer("Josh", "Long"));

		vaadinUI.filterCustomersByLastName("Long");

		then(getCustomersInGrid()).hasSize(1);
		then(getCustomersInGrid().get(getCustomersInGrid().size() - 1)).extracting("firstName", "lastName").containsExactly("Josh", "Long");
	}

	@Test
	public void shouldInitializeWithInvisibleEditor() {
		this.vaadinUI.init(this.vaadinRequest);

		then(this.editor.isVisible()).isFalse();
	}

	@Test
	public void shouldMakeEditorVisible() {
		this.vaadinUI.init(this.vaadinRequest);
		Customer first = getCustomersInGrid().get(0);
		this.vaadinUI.grid.select(first);

		then(this.editor.isVisible()).isTrue();
	}

	private void customerDataWasFilled(CustomerEditor editor, String firstName, String lastName) {
		this.editor.getFirstName().setValue(firstName);
		this.editor.getLastName().setValue(lastName);
		editor.editCustomer(new Customer(firstName, lastName));
	}

}
