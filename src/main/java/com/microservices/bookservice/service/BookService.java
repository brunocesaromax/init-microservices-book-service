package com.microservices.bookservice.service;

import com.microservices.bookservice.dto.BookDTO;
import com.microservices.bookservice.model.Book;
import com.microservices.bookservice.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    private final Map<Long, Book> cache = new HashMap<>();

    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not Found"));
    }

    public void addInCache(Book book) {
        cache.put(book.getId(), book);
    }

    public Object findInCache(Long bookId) {
        if (cache.containsKey(bookId)) {
            return cache.get(bookId);
        } else {
            Map<String, Object> availableBooks = new LinkedHashMap<>();
            availableBooks.put("message", "Livros disponíveis no momento:");
            availableBooks.put("books", cache.values());

            return availableBooks;
        }
    }

    public Integer countCached() {
        return cache.size();
    }

    @Transactional
    public Book create(BookDTO bookDTO) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDTO, book);
        book = repository.save(book);
        return book;
    }
}