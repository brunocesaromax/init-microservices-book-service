package com.microservices.bookservice.controller;

import com.microservices.bookservice.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @GetMapping(value = "/{id}/{currency}")
    public Book getBook(@PathVariable Long id, @PathVariable String currency) {
        var port = environment.getProperty("local.server.port");

        return new Book(1L, "Nigel Poulton", "Docker Deep Dive", LocalDate.now(), BigDecimal.valueOf(13.7), currency, port);
    }
}
