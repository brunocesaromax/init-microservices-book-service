package com.microservices.bookservice.controller;

import com.microservices.bookservice.proxy.CambioProxy;
import com.microservices.bookservice.service.BookService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private Environment environment;

    @Autowired
    private BookService service;

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
    @Operation(summary = "Find a specific book by your ID")
    @CircuitBreaker(name = "getBookCB", fallbackMethod = "getBookFallBack")
//    @RateLimiter(name = "getBookRL")
    @GetMapping(value = "/{id}/{currency}")
    public ResponseEntity<?> getBook(@PathVariable Long id, @PathVariable String currency) {
        var book = service.findById(id);

        var cambioResponse = cambioProxy.getCambio(book.getPrice(), "USD", currency);
        var cambio = cambioResponse.getBody();

        var port = environment.getProperty("local.server.port");
        book.setEnvironment("Book port: " + port + "\n Cambio port: " + cambio.getEnvironment());
        book.setPrice(cambio.getConvertedValue());

        logger.info("Alimentando cache:");
        service.addInCache(book);

        return ResponseEntity.ok(book);
    }

    public ResponseEntity<?> getBookFallBack(Long bookId, String currency, Throwable e) {
        logger.info("Buscando no cache:");

        Object object = service.findInCache(bookId);
        return ResponseEntity.ok(object);
    }
}
