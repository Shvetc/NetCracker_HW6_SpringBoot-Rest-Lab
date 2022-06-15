package com.netcracker.repository;

import com.netcracker.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT region FROM customer GROUP BY region", nativeQuery = true)
    List<String> getDifferentCustomersRegions();

    @Query(value = "SELECT c.id, c.region, c.last_Name, c.discount FROM customer c where c.region = :reg ", nativeQuery = true)
    List<Customer> findCustomersByRegion(@Param("reg") String reg);
}
