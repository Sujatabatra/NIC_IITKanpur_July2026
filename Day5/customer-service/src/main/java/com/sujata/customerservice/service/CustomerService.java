package com.sujata.customerservice.service;

import java.util.List;

import com.sujata.customerservice.entity.Customer;

public interface CustomerService {

	/**
	 * Creates a new customer.
	 *
	 * @param customer Customer to be created
	 * @return Saved Customer
	 */
	Customer createCustomer(Customer customer);

	/**
	 * Updates an existing customer.
	 *
	 * @param id       Customer Id
	 * @param customer Updated Customer details
	 * @return Updated Customer
	 */
	Customer updateCustomer(Long id, Customer customer);

	/**
	 * Deletes a customer by Id.
	 *
	 * @param id Customer Id
	 */
	void deleteCustomer(Long id);

	/**
	 * Returns a customer by Id.
	 *
	 * @param id Customer Id
	 * @return Customer
	 */
	Customer getCustomerById(Long id);

	/**
	 * Returns all customers.
	 *
	 * @return List of Customers
	 */
	List<Customer> getAllCustomers();

	/**
	 * Returns all customers from a city.
	 *
	 * @param city City Name
	 * @return List of Customers
	 */
	List<Customer> searchByCity(String city);

	/**
	 * Searches customers by name.
	 *
	 * @param keyword Customer Name
	 * @return List of Customers
	 */
	List<Customer> searchByName(String keyword);

}