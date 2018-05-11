package com.ea.vaadin.crud.ui;

import java.util.List;

import org.springframework.util.StringUtils;

import com.ea.vaadin.crud.entity.Customer;
import com.ea.vaadin.crud.repository.CustomerRepository;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("deprecation")
@SpringUI
public class VaadinUI extends UI {

	private static final long serialVersionUID = 1L;
	private final CustomerRepository repo;
	
	final TextField filterByLastNameField;
	private final Button addNewBtn;
	final Grid<Customer> grid;
	private final CustomerEditor editForm;

	public VaadinUI(CustomerRepository repo, CustomerEditor editForm) {
		this.repo = repo;
		this.editForm = editForm;
		this.grid = new Grid<>(Customer.class);
		this.filterByLastNameField = new TextField();
		this.addNewBtn = new Button("New customer", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		HorizontalLayout searchForm = new HorizontalLayout(filterByLastNameField, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(searchForm, grid, editForm);
		setContent(mainLayout);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("id", "firstName", "lastName");

		filterByLastNameField.setPlaceholder("Filter by last name");
		filterByLastNameField.setValueChangeMode(ValueChangeMode.LAZY); //// Replace with filtered content when user changes filter
		filterByLastNameField.addValueChangeListener(e -> filterCustomersByLastName(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editForm.editCustomer(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editForm.editCustomer(new Customer("", "")));

		// Add lister to the editor. On change, refresh data from backend and hide the form.
		editForm.setChangeHandler(() -> {
			editForm.setVisible(false);
			filterCustomersByLastName(filterByLastNameField.getValue());
		});

		// Initialize listing
		filterCustomersByLastName(null);
	}

	public void filterCustomersByLastName(String filterText) {
		final List<Customer> list;
		if (StringUtils.isEmpty(filterText)) {
			list = repo.findAll();
		} else {
			list = repo.findByLastNameStartsWithIgnoreCase(filterText);
		}
		
		grid.setItems(list);
	}

}
