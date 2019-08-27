package com.khobu.checkn.service;


import com.khobu.checkn.domain.Customer;
import com.khobu.checkn.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
	private CustomerRepository customerRepository;

    public List<Customer> retrieveAllCustomers() {
        return customerRepository.findAll();
    }

    /*public CustomerStats getCustomerStats(){
        CustomerStats customerStats = new CustomerStats();
        customerStats.setCustomerCount(customerRepository.getTotalCustomerCount());
        return customerStats;
    }*/

    public Customer findCustomerById(long id){
        return customerRepository.findById(id)
                .orElse(null);
			//.orElseThrow(() -> new Exception("Customer Not Found")); //CustomerNotFoundException(id)
    }

    public Customer saveCustomer(Customer customer){
        Customer returnValue = null;
        try{
            returnValue = customerRepository.save(customer);
        } catch (Exception ex){
        }
        return returnValue;
    }

    public Customer updateCustomer(Customer pCustomer, long id){
        return customerRepository.findById(id)
			.map(customer -> {
				customer = updateCustomer(customer, pCustomer);
				return customerRepository.save(customer);
			})
			.orElseGet(() -> {
				pCustomer.setId(id);
				return customerRepository.save(pCustomer);
			});
    }

    private Customer updateCustomer(Customer originalCustomer, Customer updatedCustomer){
        if(updatedCustomer.getFirstName() != null){
            originalCustomer.setFirstName(updatedCustomer.getFirstName());
        }

        return originalCustomer;
    }

    public boolean deleteCustomer(long id){
       return customerRepository.findById(id)
			.map(customer -> {
			    customer.setActive(false);
				customerRepository.save(customer);
				return true;
			}).orElse(false);
    }

}
