package com.microservices.bookservice.controller;

import com.microservices.bookservice.model.Book;
import com.microservices.bookservice.proxy.CambioProxy;
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

    @Autowired
    private CambioProxy cambioProxy;

    // Usando RestTemplate
    /*@GetMapping(value = "/{id}/{currency}")
    public Book getBook(@PathVariable Long id, @PathVariable String currency) {
        var book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not Found"));

        HashMap<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);

        var response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params);
        var cambio = response.getBody();

        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port);
        book.setPrice(cambio.getConvertedValue());

        return book;
    }*/

    // Usando OpenFeign
    @GetMapping(value = "/{id}/{currency}")
    public Book getBook(@PathVariable Long id, @PathVariable String currency) {
        var book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not Found"));

        var cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);

        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port + " FEIGN");
        book.setPrice(cambio.getConvertedValue());

        return book;
    }
}
