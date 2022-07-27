package com.microservices.bookservice.proxy;

import com.microservices.bookservice.response.Cambio;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "cambio-service")
public interface CambioProxy {

    Logger logger = LoggerFactory.getLogger(CambioProxy.class);

    @GetMapping(value = "/cambio-service/{amount}/{from}/{to}")
    ResponseEntity<Cambio> getCambio(@PathVariable BigDecimal amount,
                                     @PathVariable String from,
                                     @PathVariable String to);

    default boolean isOnline() {
        try {
            ResponseEntity<Cambio> cambioResponse = getCambio(BigDecimal.TEN, "USD", "BRL");
            return cambioResponse.getStatusCode().is2xxSuccessful();
        } catch (FeignException ex) {
            logger.error("Integração com API de cambio está inativa");
            return false;
        }
    }
}