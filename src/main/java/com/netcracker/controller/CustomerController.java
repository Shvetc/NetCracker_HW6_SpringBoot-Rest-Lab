package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.getEntity.GetEntity;
import com.netcracker.model.Customer;
import com.netcracker.repository.CustomerRepository;
import com.netcracker.service.CustomerServiceImpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerServiceImpi customerServiceImpi;

    @PostMapping("/customer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);

        return ResponseEntity.ok("Customer with name =" + customer.getLastName() + "has added");
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Customer customer = (Customer) GetEntity.getEntity(id, customerRepository);

        customerRepository.delete(customer);

        return ResponseEntity.ok("Customer with id = " + id + "has deleted");
    }

    @PatchMapping("/customer/{id}")
    public ResponseEntity<String> updateCustomerInPart(@PathVariable(value = "id") Integer id,
                                                       @RequestBody Customer newInfoAboutCustomer) throws ResourceNotFoundException {
        Customer customer = (Customer) GetEntity.getEntity(id, customerRepository);
        if (newInfoAboutCustomer.getLastName() != null) {
            customer.setLastName(newInfoAboutCustomer.getLastName());
        }

        if (newInfoAboutCustomer.getDiscount() > 0.0) {
            customer.setDiscount(newInfoAboutCustomer.getDiscount());
        }

        if (newInfoAboutCustomer.getRegion() != null) {
            customer.setRegion(newInfoAboutCustomer.getRegion());
        }

        customerRepository.save(customer);

        return ResponseEntity.ok("The customer with id = " + customer.getId() + "has updated");
    }

    @GetMapping("/customer")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {

        return ResponseEntity.ok((Customer) GetEntity.getEntity(id, customerRepository));
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<String> fullOverwritingCustomer(@PathVariable(value = "id") Integer id,
                                                          @RequestBody Customer newInfoAboutCustomer) throws ResourceNotFoundException {
        Customer customer = (Customer) GetEntity.getEntity(id, customerRepository);

        customer.setLastName(newInfoAboutCustomer.getLastName());
        customer.setDiscount(newInfoAboutCustomer.getDiscount());
        customer.setRegion(newInfoAboutCustomer.getRegion());

        customerRepository.save(customer);

        return ResponseEntity.ok("Customer with id = " + id + "has updated");
    }

    @GetMapping("/customer/different_regions")
    public List<String> getDifferentCustomersRegions() {
        return customerRepository.getDifferentCustomersRegions();
    }


    @GetMapping(value = "/customer/info_for_customers_lived_in_the_current_region")
    public List<String> getLastNameAndDiscountOfCustomersLivedInTheCurrentRegion(@RequestParam String reg) {
        return customerServiceImpi.getLastNameAndDiscountOfCustomersLivedInTheCurrentRegion(reg);
    }

}
