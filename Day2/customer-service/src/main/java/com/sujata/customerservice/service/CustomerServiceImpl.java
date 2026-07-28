package com.sujata.customerservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sujata.customerservice.entity.Customer;
import com.sujata.customerservice.exception.CustomerNotFoundException;
import com.sujata.customerservice.exception.DuplicateCustomerException;
import com.sujata.customerservice.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {

        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new DuplicateCustomerException(
                    "Customer with email '" + customer.getEmail() + "' already exists.");
        }

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with Id : " + id));

        if (!existingCustomer.getEmail().equalsIgnoreCase(customer.getEmail())
                && customerRepository.existsByEmail(customer.getEmail())) {

            throw new DuplicateCustomerException(
                    "Customer with email '" + customer.getEmail() + "' already exists.");
        }

        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setCity(customer.getCity());
        existingCustomer.setPhone(customer.getPhone());

        return customerRepository.save(existingCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {

        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with Id : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with Id : " + id));

        customerRepository.delete(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> searchByCity(String city) {

        return customerRepository.findByCityIgnoreCase(city);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> searchByName(String keyword) {

        return customerRepository.findByNameContainingIgnoreCase(keyword);
    }

}