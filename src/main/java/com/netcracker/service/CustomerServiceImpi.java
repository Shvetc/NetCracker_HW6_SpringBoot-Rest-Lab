package com.netcracker.service;

import com.netcracker.api.CustomerService;
import com.netcracker.model.Customer;
import com.netcracker.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpi implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<String> getLastNameAndDiscountOfCustomersLivedInTheCurrentRegion(String reg) {
        List<Customer> customers = customerRepository.findCustomersByRegion(reg);

        List<String> lastNamesAndDiscounts = new ArrayList<>();

        for (Customer customer : customers) {
            lastNamesAndDiscounts.add(customer.getLastName() + " " + customer.getDiscount());
        }

        return lastNamesAndDiscounts;
    }
}
