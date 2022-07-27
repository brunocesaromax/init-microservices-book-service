package com.microservices.bookservice.actuator;

import com.microservices.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerEndpoint(id = "books")
@Component
public class BookActuatorEndpoint {

    @Autowired
    private BookRepository repository;

    @GetMapping
    public List<String> bookRoot() {
        return List.of("books-counting");
    }

    @GetMapping("/books-counting")
    public Map<String, Object> counting() {
        final HashMap<String, Object> response = new HashMap<>();
        response.put("qtd", repository.count());
        return response;
    }
}