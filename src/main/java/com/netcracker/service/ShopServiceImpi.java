package com.netcracker.service;

import com.netcracker.api.ShopService;
import com.netcracker.model.Shop;
import com.netcracker.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopServiceImpi implements ShopService {

    @Autowired
    ShopRepository shopRepository;

    public List<String> getNamesOfShopsInTheCurrentRegions(String region_1, String region_2) {
        List<Shop> shops = shopRepository.getNamesOfShopsInTheCurrentRegions(region_1, region_2);
        List<String> namesOfShops = new ArrayList<>();
        for (Shop shop : shops) {
            namesOfShops.add(shop.getName());
        }

        return namesOfShops;
    }
}
