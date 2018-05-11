package com.ea.vaadin.crud.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.ea.vaadin.crud.entity.Customer;
import com.ea.vaadin.crud.event.ChangeHandler;
import com.ea.vaadin.crud.repository.CustomerRepository;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A simple example to introduce building forms. Customer New/Edit form.
 * In a real world application you'll most likely using a common super class for all your forms - less code, better UX. 
 * See e.g. AbstractForm in Viritin (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@UIScope
public class CustomerEditor extends VerticalLayout {
	
	private static final long serialVersionUID = 6466166317601023237L;
	
	private TextField firstName = new TextField("First name");
	private TextField lastName = new TextField("Last name");
	
	private Button save = new Button("Save", VaadinIcons.CHECK);
	private Button cancel = new Button("Cancel");
	private Button delete = new Button("Delete", VaadinIcons.TRASH);
	private CssLayout actions = new CssLayout(save, cancel, delete);
	
	private Binder<Customer> binder = new Binder<>(Customer.class);

	private final CustomerRepository repository;
	private Customer customer;

	@Autowired
	public CustomerEditor(CustomerRepository repository) {
		this.repository = repository;
		
		binder.bindInstanceFields(this);
		addComponents(firstName, lastName, actions);
		setSpacing(true);
		setVisible(false);
		
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		save.addClickListener(e -> repository.save(customer));
		delete.addClickListener(e -> repository.delete(customer));
		cancel.addClickListener(e -> editCustomer(customer));
	}

	public final void editCustomer(Customer c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		
		final boolean persisted = c.getId() != null;
		if (persisted) {
			customer = repository.findById(c.getId()).get(); // Find fresh entity for editing
		}
		else {
			customer = c;
		}
		//cancel.setEnabled(!persisted);  enable disabled if value changed

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(customer);

		setVisible(true);

		save.focus(); // A hack to ensure the whole form is visible
		firstName.selectAll(); // Select all text in firstName field automatically
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TextField getFirstName() {
		return firstName;
	}

	public TextField getLastName() {
		return lastName;
	}

	public Button getSave() {
		return save;
	}

	public Button getCancel() {
		return cancel;
	}

	public Button getDelete() {
		return delete;
	}

	public CssLayout getActions() {
		return actions;
	}

	public Binder<Customer> getBinder() {
		return binder;
	}

	public CustomerRepository getRepository() {
		return repository;
	}

	public Customer getCustomer() {
		return customer;
	}
	
	
	
}
