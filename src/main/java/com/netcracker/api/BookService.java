package com.netcracker.api;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BookService {
    List<String> getNamesBookAndPricesByWordOrPrice (String word, Double price);

    Map<String, Double> getDifferentNamesOfBooksAndThemPrice();

}
