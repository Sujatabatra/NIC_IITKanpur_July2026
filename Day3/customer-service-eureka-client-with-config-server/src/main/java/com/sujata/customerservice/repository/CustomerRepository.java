package com.sujata.customerservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sujata.customerservice.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	/**
	 * Checks whether a customer with the given email already exists.
	 */
	boolean existsByEmail(String email);

	/**
	 * Finds a customer by email.
	 */
	Optional<Customer> findByEmail(String email);

	/**
	 * Returns all customers from the given city.
	 */
	List<Customer> findByCityIgnoreCase(String city);

	/**
	 * Searches customers whose name contains the given keyword.
	 */
	List<Customer> findByNameContainingIgnoreCase(String keyword);

}