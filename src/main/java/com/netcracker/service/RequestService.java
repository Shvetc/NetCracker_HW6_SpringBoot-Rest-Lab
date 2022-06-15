package com.netcracker.service;

import com.netcracker.model.Book;
import com.netcracker.model.Customer;
import com.netcracker.model.Purchase;
import com.netcracker.model.Shop;
import com.netcracker.repository.BookRepository;
import com.netcracker.repository.CustomerRepository;
import com.netcracker.repository.PurchaseRepository;
import com.netcracker.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RequestService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    public Map<String, Double> getDifferentNamesOfBooksAndThemPrice() {
        ArrayList<String> namesBook = new ArrayList<>(bookRepository.getDifferentBookNames());
        ArrayList<Double> prices = new ArrayList<>(bookRepository.getDifferentBookPrices());

        Map<String, Double> info = new HashMap<>();

        for (int i = 0; i < namesBook.size(); i++) {
            info.put(namesBook.get(i), prices.get(i));
        }

        return info;
    }

    public List<String> getLastNameAndDiscountOfCustomersLivedInTheCurrentRegion(String region) {
        List<Customer> customers = customerRepository.findCustomersByRegion(region);

        List<String> lastNamesAndDiscounts = new ArrayList<>();

        for (Customer customer : customers) {
            lastNamesAndDiscounts.add(customer.getLastName() + " " + customer.getDiscount());
        }

        return lastNamesAndDiscounts;
    }

    public List<String> getNamesOfShopsInTheCurrentRegions(String region_1, String region_2) {
        List<Shop> shops = shopRepository.getNamesOfShopsInTheCurrentRegions(region_1, region_2);
        List<String> namesOfShops = new ArrayList<>();
        for (Shop shop : shops) {
            namesOfShops.add(shop.getName());
        }

        return namesOfShops;
    }

    public  List<String> getNamesBookAndPricesByWordOrPrice (String word, Double price)
    {
        List<Book> books = bookRepository.getNamesBookAndPricesByWordOrPrice(word, price);

        List<String> result = new ArrayList<>();

        for (Book book : books) {
            result.add(book.getName() + " " + book.getCost());
        }

        return  result;
    }
}
