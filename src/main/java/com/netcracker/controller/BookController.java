package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.getEntity.GetEntity;
import com.netcracker.model.Book;
import com.netcracker.repository.BookRepository;
import com.netcracker.service.BookServiceImpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookServiceImpi bookServiceImpi;

    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Book book = (Book) GetEntity.getEntity(id, bookRepository);
        bookRepository.delete(book);
        return ResponseEntity.ok("Book with id = " + id + "deleted");
    }

    @PatchMapping("/book/{id}")
    public ResponseEntity<String> updateBookInPart(@PathVariable(value = "id") Integer id,
                                                   @RequestBody Book newInfoAboutBook) throws ResourceNotFoundException {
        Book updatedBook = (Book) GetEntity.getEntity(id, bookRepository);
        if (newInfoAboutBook.getName() != null) updatedBook.setName(newInfoAboutBook.getName());
        if (newInfoAboutBook.getCost() > 0.0) updatedBook.setCost(newInfoAboutBook.getCost());
        if (newInfoAboutBook.getStorage() != null) updatedBook.setStorage(newInfoAboutBook.getStorage());
        if (newInfoAboutBook.getQuantity() > 0) updatedBook.setQuantity(newInfoAboutBook.getQuantity());
        bookRepository.save(updatedBook);

        return ResponseEntity.ok("The book with id = " + updatedBook.getId() + "is updated");
    }

    @PostMapping("/book")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        bookRepository.save(book);

        return ResponseEntity.ok("The book with current id = " + book.getId() + "has added");
    }

    @GetMapping("/book")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok((Book)GetEntity.getEntity(id, bookRepository));
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<String> updateBook(@PathVariable(value = "id") Integer id,
                                             @RequestBody Book newInfoAboutBook) throws ResourceNotFoundException {
        Book book = (Book)GetEntity.getEntity(id, bookRepository);
        book.copyData(newInfoAboutBook);
        bookRepository.save(book);

        return ResponseEntity.ok("The book with id =" + book.getId() + "updated");
    }

    @GetMapping("/book/different_names_and_prices")
    public Map<String, Double> getDifferentBookNamesAndPrices() {
        return bookServiceImpi.getDifferentNamesOfBooksAndThemPrice();
    }

    @GetMapping("/book/names_prices_by_word_or_price")
    public List<String> getNamesBookAndPricesByWordOrPrice(@RequestParam String word,
                                                           @RequestParam Double price) {
        return bookServiceImpi.getNamesBookAndPricesByWordOrPrice(word, price);
    }
}
