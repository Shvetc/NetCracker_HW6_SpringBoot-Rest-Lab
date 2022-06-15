package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.getEntity.GetEntity;
import com.netcracker.model.Book;
import com.netcracker.model.Customer;
import com.netcracker.model.Purchase;
import com.netcracker.model.Shop;
import com.netcracker.repository.BookRepository;
import com.netcracker.repository.CustomerRepository;
import com.netcracker.repository.PurchaseRepository;
import com.netcracker.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class PurchaseController {
    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ShopRepository shopRepository;

    @DeleteMapping("/purchase/{id}")
    public ResponseEntity<String> deletePurchase(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {

        Purchase purchase = (Purchase) GetEntity.getEntity(id, purchaseRepository);

        purchaseRepository.deleteById(purchase.getId());

        return ResponseEntity.ok("Purchase with id = " + id + "deleted");
    }

    @PatchMapping("/purchase/{id}")
    public ResponseEntity<String> updatePurchaseInPart(@PathVariable(value = "id") Integer id,
                                                       @RequestBody Purchase newInfoAboutPurchase) throws ResourceNotFoundException {
        Purchase purchase = (Purchase) GetEntity.getEntity(id, purchaseRepository);

        if (newInfoAboutPurchase.getDate() != null)
            purchase.setDate(newInfoAboutPurchase.getDate());

        if (newInfoAboutPurchase.getPrice() > 0.0)
            purchase.setPrice(newInfoAboutPurchase.getPrice());

        if (newInfoAboutPurchase.getQuantity() > 0)
            purchase.setQuantity(newInfoAboutPurchase.getQuantity());

        if (newInfoAboutPurchase.getBook() != null) {
            Integer idBook = newInfoAboutPurchase.getBook().getId();
            Book book = (Book) GetEntity.getEntity(idBook, bookRepository);
            purchase.setBook(book);
        }

        if (newInfoAboutPurchase.getCustomer() != null) {
            Integer idCustomer = newInfoAboutPurchase.getCustomer().getId();
            Customer customer = (Customer) GetEntity.getEntity(idCustomer, customerRepository);
            purchase.setCustomer(customer);
        }

        if (newInfoAboutPurchase.getShop() != null) {
            Integer idShop = newInfoAboutPurchase.getShop().getId();
            Shop shop = (Shop) GetEntity.getEntity(idShop, shopRepository);
            purchase.setShop(shop);
        }

        purchaseRepository.save(purchase);

        return ResponseEntity.ok("Purchase with id = " + id + "has updated");
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> addPurchase(@RequestBody Purchase purchase) throws ResourceNotFoundException {
        Integer idBook = purchase.getBook().getId();
        Integer idCustomer = purchase.getCustomer().getId();
        Integer idShop = purchase.getShop().getId();

        purchase.setBook((Book) GetEntity.getEntity(idBook, bookRepository));
        purchase.setCustomer((Customer) GetEntity.getEntity(idCustomer, customerRepository));
        purchase.setShop((Shop) GetEntity.getEntity(idShop, shopRepository));

        purchaseRepository.save(purchase);

        return ResponseEntity.ok("Purchase with customer :" + purchase.getCustomer() + "has added");
    }

    @GetMapping("/purchase")
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {

        Purchase purchase = (Purchase) GetEntity.getEntity(id, purchaseRepository);

        return ResponseEntity.ok().body(purchase);
    }

    @PutMapping("/purchase/{id}")
    public ResponseEntity<String> updatePurchase(@PathVariable(value = "id") Integer id,
                                                 @RequestBody Purchase newInfoAboutPurchase) throws ResourceNotFoundException {
        Purchase purchase = (Purchase) GetEntity.getEntity(id, purchaseRepository);

        purchase.setDate(newInfoAboutPurchase.getDate());
        purchase.setBook(newInfoAboutPurchase.getBook());
        purchase.setCustomer(newInfoAboutPurchase.getCustomer());
        purchase.setShop(newInfoAboutPurchase.getShop());
        purchase.setPrice(newInfoAboutPurchase.getPrice());
        purchase.setQuantity(newInfoAboutPurchase.getQuantity());

        purchaseRepository.save(purchase);

        return ResponseEntity.ok("Purchase with Customer =" + purchase.getCustomer() + "has added");
    }

    @GetMapping("/purchase/get_different_months")
    public List<String> getDifferentMonths() {
        return purchaseRepository.getDifferentMonths();
    }

    @GetMapping("/purchase/get_lastnames_and_shops")
    public List<List<String>> getLastnamesAndShops() {
        return purchaseRepository.getCustomersLastNamesAndShopsNames();
    }

    @GetMapping("/purchase/info_about_purchases")
    public List<List<String>> getInfoAboutPurchase() {
        return purchaseRepository.getInfoAboutPurchase();
    }

    @GetMapping("/purchase/shop_revenue")
    public List<List<String>> getInfoAboutShopsRevenue(@RequestParam Double cost) {
        return purchaseRepository.getInfoAboutShopsRevenue(cost);
    }

    @GetMapping("/purchase/get_info_about_purchase_in_the_month")
    public List<List<String>> getReceivePurchaseInfoAfterMarch(@RequestParam Integer indMonth) {
        return purchaseRepository.getReceivePurchaseInfoAfterMonth(indMonth);
    }

    @GetMapping("/purchase/data_on_the_purchase_of_books")
    public List<List<String>> getDataOnThePurchaseOfBooksPurchasedInTheStorageRegion(@RequestParam Integer count) {
        return purchaseRepository.getDataOnThePurchaseOfBooksPurchasedInTheStorageRegion(count);
    }
}
