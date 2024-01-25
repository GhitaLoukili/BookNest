package com.ghita.booknestbackend.controller;


import com.ghita.booknestbackend.domain.Book;
import com.ghita.booknestbackend.repository.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository=bookRepository;
    }
    @GetMapping("/books")
    public Iterable<Book> getCars() {
        return bookRepository.findAll();
    };
}
