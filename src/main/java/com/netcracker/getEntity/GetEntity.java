package com.netcracker.getEntity;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.repository.BookRepository;
import com.netcracker.repository.CustomerRepository;
import com.netcracker.repository.PurchaseRepository;
import com.netcracker.repository.ShopRepository;

public abstract class GetEntity {
    public static Object getEntity(int id, Object repository) throws ResourceNotFoundException {
        if (repository instanceof BookRepository) {
            BookRepository bookRepository = (BookRepository) repository;
            return bookRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Book with id = " + id + " not found"));
        }

        if (repository instanceof CustomerRepository) {
            CustomerRepository customerRepository = (CustomerRepository) repository;

            return customerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer with id = " + id + " not found"));
        }

        if (repository instanceof ShopRepository) {
            ShopRepository shopRepository = (ShopRepository) repository;

            return shopRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Shop with id = " + id + " not found"));
        }

        if (repository instanceof PurchaseRepository) {
            PurchaseRepository purchaseRepository = (PurchaseRepository) repository;

            return purchaseRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Purchase with id = " + id + " not found"));
        }

        return new ResourceNotFoundException("Object not found");
    }
}
