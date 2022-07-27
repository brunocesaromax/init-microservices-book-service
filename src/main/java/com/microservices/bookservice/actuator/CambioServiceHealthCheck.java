package com.microservices.bookservice.actuator;

import com.microservices.bookservice.proxy.CambioProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("cambioServiceHealth")
public class CambioServiceHealthCheck implements HealthIndicator {

    @Autowired
    private CambioProxy cambioProxy;

    @Override
    public Health health() {
        return cambioProxy.isOnline()
                ? Health.up().withDetail("message", "Cambio service está ativo").build()
                : Health.down().withDetail("message", "Falha de comunicação, cambio service está inativo").build();
    }
}
