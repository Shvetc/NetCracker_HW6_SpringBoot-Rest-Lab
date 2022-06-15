package com.netcracker.api;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<String> getLastNameAndDiscountOfCustomersLivedInTheCurrentRegion(String reg);
}
