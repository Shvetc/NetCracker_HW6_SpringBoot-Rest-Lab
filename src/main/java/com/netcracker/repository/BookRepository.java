package com.netcracker.repository;

import com.netcracker.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT name FROM book GROUP BY name", nativeQuery = true)
    List<String> getDifferentBookNames();

    @Query(value = "SELECT cost FROM book GROUP BY cost", nativeQuery = true)
    List<Double> getDifferentBookPrices();

    @Query(value = "SELECT b.id, b.name, b.cost, b.quantity, b.storage FROM book b WHERE b.name LIKE %:word% OR " +
            "b.cost > :price ORDER BY b.name, b.cost DESC", nativeQuery = true)
    List<Book> getNamesBookAndPricesByWordOrPrice(@Param("word") String word, @Param("price") Double price);

}
