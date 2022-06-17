package com.microservices.bookservice.proxy;

import com.microservices.bookservice.response.Cambio;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "cambio-service")
public interface CambioProxy {

    @GetMapping(value = "/cambio-service/{amount}/{from}/{to}")
    Cambio getCambio(@PathVariable BigDecimal amount,
                     @PathVariable String from,
                     @PathVariable String to);
}