package com.microservices.bookservice.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class BookRetrieve implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal price;

    private String title;

    public BookRetrieve() {
    }

    public BookRetrieve(Long id, BigDecimal price, String title) {
        this.id = id;
        this.price = price;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BookRetrieve{" +
               "id=" + id +
               ", price=" + price +
               ", title='" + title + '\'' +
               '}';
    }
}
