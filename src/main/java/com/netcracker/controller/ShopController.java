package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.getEntity.GetEntity;
import com.netcracker.model.Shop;
import com.netcracker.repository.ShopRepository;
import com.netcracker.service.ShopServiceImpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class ShopController {

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ShopServiceImpi shopServiceImpi;

    @DeleteMapping("/shop/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {

        Shop shop = (Shop) GetEntity.getEntity(id, shopRepository);

        shopRepository.deleteById(shop.getId());

        return ResponseEntity.ok("Shop with id = " + id + "has deleted");
    }

    @PatchMapping("/shop/{id}")
    public ResponseEntity<String> updateShopInPart(@PathVariable(value = "id") Integer id,
                                                   @RequestBody Shop newInfoAboutShop) throws ResourceNotFoundException {
        Shop shop = (Shop) GetEntity.getEntity(id, shopRepository);

        if (newInfoAboutShop.getName() != null)
            shop.setName(newInfoAboutShop.getName());
        if (newInfoAboutShop.getRegion() != null)
            shop.setRegion(newInfoAboutShop.getRegion());
        if (newInfoAboutShop.getCommission() >= 0)
            shop.setCommission(newInfoAboutShop.getCommission());

        shopRepository.save(shop);

        return ResponseEntity.ok("Shop with id = " + id + "has updated");
    }

    @PostMapping("/shop")
    public ResponseEntity<String> addShop(@RequestBody Shop shop) {

        shopRepository.save(shop);

        return ResponseEntity.ok("Shop with customer =" + shop.getName() + "has added");
    }

    @GetMapping("/shop")
    public List<Shop> getAllShop() {
        return shopRepository.findAll();
    }

    @GetMapping("/shop/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {

        Shop shop = (Shop) GetEntity.getEntity(id, shopRepository);

        return ResponseEntity.ok().body(shop);
    }

    @PutMapping("/shop/{id}")
    public ResponseEntity<String> updateShop(@PathVariable(value = "id") Integer id,
                                           @RequestBody Shop newInfoAboutShop) throws ResourceNotFoundException {
        Shop shop = (Shop) GetEntity.getEntity(id, shopRepository);

        shop.setName(newInfoAboutShop.getName());
        shop.setRegion(newInfoAboutShop.getRegion());
        shop.setCommission(newInfoAboutShop.getCommission());

        shopRepository.save(shop);

        return ResponseEntity.ok("Shop with id = " + id + "has fully updated");
    }

    @GetMapping(value = "/shop/shops_in_the_current_regions")
    public List<String> getNamesOfShopsInTheCurrentRegions(@RequestParam String region_1,
                                                           @RequestParam String region_2) {
        return shopServiceImpi.getNamesOfShopsInTheCurrentRegions(region_1, region_2);
    }
}
