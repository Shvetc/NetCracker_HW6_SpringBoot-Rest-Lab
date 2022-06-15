package com.netcracker.repository;

import com.netcracker.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    @Query(value = "SELECT DISTINCT to_char(date, 'month') FROM purchase", nativeQuery = true)
    List<String> getDifferentMonths();

    @Query(value = "SELECT customer.last_name, shop.name FROM purchase, customer, shop " +
            "WHERE purchase.customer_id = customer.id AND " +
            "purchase.shop_id = shop.id", nativeQuery = true)
    List<List<String>> getCustomersLastNamesAndShopsNames();

    @Query(value = "SELECT p.date, c.last_name, c.discount, b.name, p.quantity" +
            " FROM purchase p, customer c, book b WHERE p.customer_id = c.id AND p.book_id = b.id", nativeQuery = true)
    List<List<String>> getInfoAboutPurchase();

    @Query(value = "SELECT p.id, c.last_name, p.date, p.price FROM purchase p, customer c " +
            "WHERE p.customer_id = c.id AND p.price >= :cost", nativeQuery = true)
    List<List<String>> getInfoAboutShopsRevenue(Double cost);

    @Query(value = "SELECT c.last_name, s.region, p.date\n" +
            "FROM purchase p, customer c, shop s\n" +
            "WHERE p.customer_id = c.id AND p.shop_id = s.id\n" +
            "AND c.region = s.region AND to_number(to_char(date, 'mm'),'99') >= 3 ORDER BY c.last_name", nativeQuery = true)
    List<List<String>> getReceivePurchaseInfoAfterMarch();

    @Query(value = "SELECT b.name, b.storage, p.quantity, p.price FROM purchase p, shop s, book b\n" +
            "WHERE p.book_id = b.id AND p.shop_id = s.id\n" +
            "  AND b.quantity > 10 ORDER BY p.price", nativeQuery = true)
    List<List<String>> getDataOnThePurchaseOfBooksPurchasedInTheStorageRegion();


}
