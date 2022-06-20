package com.microservices.bookservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Foo bar")
@RestController
@RequestMapping("book-service")
public class FooBarController {

    private final Logger logger = LoggerFactory.getLogger(FooBarController.class);

    @Operation(summary = "Foo bar")
    @GetMapping("/foo-bar")
//    @Retry(name = "foo-bar", fallbackMethod = "fallbackMethod")
    //Comando para teste do circuit breaker: $watch -n 0.1 "curl http://localhost:8765/book-service/foo-bar"
    //@CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
//    @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    public String fooBar() {
        logger.info("Request to foo-bar is received!");
//        var response = new RestTemplate().getForEntity("http://localhost:8080/foo-bar", String.class);
//        return response.getBody();
        return "Foo-Bar!!!";
    }

    //Pode se customizar vários métodos fallbacks com diferentes exceções e assinaturas diferentes,
    // dependendo da necessidade da aplicação
    public String fallbackMethod(Exception ex) {
        return "fallbackMethod foo-bar!!!";
    }
}