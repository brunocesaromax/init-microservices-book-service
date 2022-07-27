package com.microservices.bookservice.actuator;

import com.microservices.bookservice.service.BookService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomizedMetricsConfig {

    @Bean
    public MeterBinder countMessages(BookService bookService) {
        return meterRegistry -> Gauge.builder("cachedBooks.qtd", bookService::countCached).register(meterRegistry);
    }
}