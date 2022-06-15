package com.netcracker.controller;

import com.fasterxml.jackson.databind.util.ObjectBuffer;
import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Customer;
import com.netcracker.repository.CustomerRepository;
import com.netcracker.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RequestService requestService;

    @PostMapping("/customer")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found  with id = " + id));
        customerRepository.deleteById(id);

        return ResponseEntity.ok("Customer with id = " + id + " deleted");
    }

    @PatchMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomerInPart(@PathVariable(value = "id") Integer id,
                                                         @RequestBody Customer newInfoAboutCustomer) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id = " + id));

        if (newInfoAboutCustomer.getLastName() != null)
            customer.setLastName(newInfoAboutCustomer.getLastName());
        if (newInfoAboutCustomer.getDiscount() != null)
            customer.setDiscount(newInfoAboutCustomer.getDiscount());
        if (newInfoAboutCustomer.getRegion() != null)
            customer.setRegion(newInfoAboutCustomer.getRegion());

        final Customer updatedCustomer = customerRepository.save(customer);

        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping("/customer")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {

        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id = " + id)
        );

        return ResponseEntity.ok(customer);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> fullOverwritingCustomer(@PathVariable(value = "id") Integer id,
                                                            @RequestBody Customer newInfoAboutCustomer) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id = " + id));

        customer.setLastName(newInfoAboutCustomer.getLastName());
        customer.setDiscount(newInfoAboutCustomer.getDiscount());
        customer.setRegion(newInfoAboutCustomer.getRegion());

        final Customer updatedCustomer = customerRepository.save(customer);

        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping("/customer/different_regions")
    public List<String> getDifferentCustomersRegions() {
        return customerRepository.getDifferentCustomersRegions();
    }

    @GetMapping(value = "/customer/info_for_customers_lived_in_the_current_region")
    public List<String> getLastNameAndDiscountOfCustomersLivedInTheCurrentRegion(@RequestParam(required = false, defaultValue = "Нижегородский") String region) {

        return requestService.getLastNameAndDiscountOfCustomersLivedInTheCurrentRegion(region);
    }

}
