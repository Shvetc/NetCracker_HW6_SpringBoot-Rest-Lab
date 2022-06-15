package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Book;
import com.netcracker.repository.BookRepository;
import com.netcracker.service.RequestService;
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
    RequestService requestService;

    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book not found with id = " + id));
        bookRepository.deleteById(id);

        return ResponseEntity.ok("Book with id = " + id + "deleted");
    }

    @PatchMapping("/book/{id}")
    public ResponseEntity<Book> updateBookInPart(@PathVariable(value = "id") Integer id,
                                                    @RequestBody Book newInfoAboutBook) throws ResourceNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Book not found with id =" + id));

        if (newInfoAboutBook.getName() != null)
            book.setName(newInfoAboutBook.getName());
        if (newInfoAboutBook.getQuantity() != null)
            book.setQuantity((newInfoAboutBook.getQuantity()));
        if (newInfoAboutBook.getCost() != null)
            book.setCost(newInfoAboutBook.getCost());
        if (newInfoAboutBook.getStorage() != null)
            book.setStorage(newInfoAboutBook.getStorage());

        final Book updatedBook = bookRepository.save(book);

        return ResponseEntity.ok(updatedBook);
    }

    @PostMapping("/book")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/book")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {

        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book not found with id = " + id)
        );

        return ResponseEntity.ok().body(book);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Integer id,
                                           @RequestBody Book newInfoAboutBook) throws ResourceNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book not found with id = " + id));

        book.setName(newInfoAboutBook.getName());
        book.setQuantity(newInfoAboutBook.getQuantity());
        book.setStorage(newInfoAboutBook.getStorage());
        book.setCost(newInfoAboutBook.getCost());

        final Book updatedBook = bookRepository.save(book);

        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/book/different_names_and_prices")
    public Map<String, Double> getDifferentBookNamesAndPrices(){
        return  requestService.getDifferentNamesOfBooksAndThemPrice();
    }

    @GetMapping("/book/names_prices_by_word_or_price")
    public List<String> getNamesBookAndPricesByWordOrPrice(@RequestParam(required = false, defaultValue = "Windows") String word,
                                                           @RequestParam(required = false, defaultValue = "20000") Double price)
    {
        return requestService.getNamesBookAndPricesByWordOrPrice(word, price);
    }
}
