package com.netcracker.api;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShopService {
    List<String> getNamesOfShopsInTheCurrentRegions(String region_1, String region_2);
}
