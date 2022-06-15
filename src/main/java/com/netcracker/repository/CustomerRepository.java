package com.netcracker.repository;

import com.netcracker.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT region FROM customer GROUP BY region", nativeQuery = true)
    List<String> getDifferentCustomersRegions();

    @Query(value = "SELECT id, region, last_Name, discount FROM customer where region = ?1 ", nativeQuery = true)
    List<Customer> findCustomersByRegion(String region);
}
