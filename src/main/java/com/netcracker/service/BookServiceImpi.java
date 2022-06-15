package com.netcracker.service;

import com.netcracker.api.BookService;
import com.netcracker.model.Book;
import com.netcracker.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpi implements BookService {
    @Autowired
    BookRepository bookRepository;

    public List<String> getNamesBookAndPricesByWordOrPrice (String word, Double price)
    {
        List<Book> books = bookRepository.getNamesBookAndPricesByWordOrPrice(word, price);

        List<String> result = new ArrayList<>();

        for (Book book : books) {
            result.add(book.getName() + " " + book.getCost());
        }

        return  result;
    }

    public Map<String, Double> getDifferentNamesOfBooksAndThemPrice() {
        ArrayList<String> namesBook = new ArrayList<>(bookRepository.getDifferentBookNames());
        ArrayList<Double> prices = new ArrayList<>(bookRepository.getDifferentBookPrices());

        Map<String, Double> info = new HashMap<>();

        for (int i = 0; i < namesBook.size(); i++) {
            info.put(namesBook.get(i), prices.get(i));
        }

        return info;
    }
}
