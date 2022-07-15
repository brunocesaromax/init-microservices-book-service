package com.microservices.bookservice.service;

import org.springframework.stereotype.Service;

@Service
public class UserAuthenticatedMockService {

    public boolean isAuthenticated() {
//        return true;
        return false;
    }

    public String getUsername() {
        return "example@mail.com";
    }
}
