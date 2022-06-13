package com.microservices.bookservice.controller;

import com.microservices.bookservice.model.Book;
import com.microservices.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository repository;

    @GetMapping(value = "/{id}/{currency}")
    public Book getBook(@PathVariable Long id, @PathVariable String currency) {
        var book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not Found"));

        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port);

        return book;
    }
}
