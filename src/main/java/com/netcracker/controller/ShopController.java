package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Shop;
import com.netcracker.repository.ShopRepository;
import com.netcracker.service.RequestService;
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
    RequestService requestService;

    @DeleteMapping("/shop/{id}")
    public String deleteShop(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        shopRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shop not found with id = " + id));
        shopRepository.deleteById(id);

        return "Shop with id = " + id + " deleted";
    }

    @PatchMapping("/shop/{id}")
    public ResponseEntity<Shop> updateShopInPart(@PathVariable(value = "id") Integer id,
                                                 @RequestBody Shop newInfoAboutShop) throws ResourceNotFoundException {
        Shop shop = shopRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shop not found with id = " + id));

        if (newInfoAboutShop.getName() != null)
            shop.setName(newInfoAboutShop.getName());
        if (newInfoAboutShop.getRegion() != null)
            shop.setRegion(newInfoAboutShop.getRegion());
        if (newInfoAboutShop.getCommission() != null)
            shop.setCommission(newInfoAboutShop.getCommission());

        final Shop updatedShop = shopRepository.save(shop);

        return ResponseEntity.ok(updatedShop);
    }

    @PostMapping("/shop")
    public Shop addShop(@RequestBody Shop shop) {
        return shopRepository.save(shop);
    }

    @GetMapping("/shop")
    public List<Shop> getAllShop() {
        return shopRepository.findAll();
    }

    @GetMapping("/shop/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {

        Shop shop = shopRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shop not found with id = " + id)
        );

        return ResponseEntity.ok().body(shop);
    }

    @PutMapping("/shop/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable(value = "id") Integer id,
                                           @RequestBody Shop newInfoAboutShop) throws ResourceNotFoundException {
        Shop shop = shopRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shop not found with id = " + id));

        shop.setName(newInfoAboutShop.getName());
        shop.setRegion(newInfoAboutShop.getRegion());
        shop.setCommission(newInfoAboutShop.getCommission());

        final Shop updatedShop = shopRepository.save(shop);

        return ResponseEntity.ok(updatedShop);
    }

    @GetMapping(value = "/shop/shops_in_the_current_regions")
    public List<String> getNamesOfShopsInTheCurrentRegions(@RequestParam(required = false, defaultValue = "Советский")
                                                               String region_1,
                                                           @RequestParam(required = false, defaultValue = "Сормовский")
                                                                String region_2) {
        return requestService.getNamesOfShopsInTheCurrentRegions(region_1, region_2);
    }
}
