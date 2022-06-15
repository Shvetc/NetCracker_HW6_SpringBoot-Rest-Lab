package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
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
    public String deletePurchase(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        purchaseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Purchase not found with id = " + id));
        purchaseRepository.deleteById(id);

        return "Purchase with id = " + id + "deleted";
    }

    @PatchMapping("/purchase/{id}")
    public ResponseEntity<Purchase> updatePurchaseInPart(@PathVariable(value = "id") Integer id,
                                                         @RequestBody Purchase newInfoAboutPurchase) throws ResourceNotFoundException {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Purchase not found with id = " + id));

        if (newInfoAboutPurchase.getDate() != null)
            purchase.setDate(newInfoAboutPurchase.getDate());
        if (newInfoAboutPurchase.getPrice() != null)
            purchase.setPrice(newInfoAboutPurchase.getPrice());
        if (newInfoAboutPurchase.getQuantity() != null)
            purchase.setQuantity(newInfoAboutPurchase.getQuantity());

        if (newInfoAboutPurchase.getBook() != null) {
            Integer idBook = newInfoAboutPurchase.getBook().getId();
            Book book = bookRepository.findById(idBook).orElseThrow(
                    () -> new ResourceNotFoundException("Book not found with id = " + idBook));
            purchase.setBook(book);
        }

        if (newInfoAboutPurchase.getCustomer() != null) {
            Integer idCustomer = newInfoAboutPurchase.getCustomer().getId();
            Customer customer = customerRepository.findById(idCustomer).orElseThrow(
                    () -> new ResourceNotFoundException("Customer not found with id = " + idCustomer));
            purchase.setCustomer(customer);
        }

        if (newInfoAboutPurchase.getShop() != null) {
            Integer idShop = newInfoAboutPurchase.getShop().getId();
            Shop shop = shopRepository.findById(idShop).orElseThrow(
                    () -> new ResourceNotFoundException("Shop not found with id = " + idShop));
            purchase.setShop(shop);
        }

        final Purchase purchaseUpdated = purchaseRepository.save(purchase);

        return ResponseEntity.ok(purchaseUpdated);
    }

    @PostMapping("/purchase")
    public Purchase addPurchase(@RequestBody Purchase purchase) throws ResourceNotFoundException {
        purchase.setBook(bookRepository.findById(purchase.getBook().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Book not found with id = " + purchase.getBook().getId())));
        purchase.setCustomer(customerRepository.findById(purchase.getCustomer().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id = " + purchase.getCustomer().getId())));
        purchase.setShop((shopRepository.findById(purchase.getShop().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Shop not found with id = " + purchase.getShop().getId()))));

        return purchaseRepository.save(purchase);
    }

    @GetMapping("/purchase")
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {

        Purchase purchase = purchaseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Purchase not found with id = " + id)
        );

        return ResponseEntity.ok().body(purchase);
    }

    @PutMapping("/purchase/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable(value = "id") Integer id,
                                                   @RequestBody Purchase newInfoAboutPurchase) throws ResourceNotFoundException {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Purchase not found with id = " + id));

        purchase.setDate(newInfoAboutPurchase.getDate());
        purchase.setBook(newInfoAboutPurchase.getBook());
        purchase.setCustomer(newInfoAboutPurchase.getCustomer());
        purchase.setShop(newInfoAboutPurchase.getShop());
        purchase.setPrice(newInfoAboutPurchase.getPrice());
        purchase.setQuantity(newInfoAboutPurchase.getQuantity());

        final Purchase purchaseUpdated = purchaseRepository.save(purchase);

        return ResponseEntity.ok(purchaseUpdated);
    }

    @GetMapping("/purchase/get_different_months")
    public List<String> getDifferentMonths() {
        return purchaseRepository.getDifferentMonths();
    }

    @GetMapping("/purchase/get_lastnames_and_shops")
    public  List<List<String>> getLastnamesAndShops() {
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
    public List<List<String>> getReceivePurchaseInfoAfterMarch(@RequestParam  Integer indMonth) {
        return purchaseRepository.getReceivePurchaseInfoAfterMonth(indMonth);
    }

    @GetMapping("/purchase/data_on_the_purchase_of_books")
    public List<List<String>> getDataOnThePurchaseOfBooksPurchasedInTheStorageRegion(@RequestParam Integer count) {
        return purchaseRepository.getDataOnThePurchaseOfBooksPurchasedInTheStorageRegion(count);
    }
}
