package com.microservices.bookservice.service;

import org.springframework.stereotype.Service;

@Service
public class UserNotAuthenticatedMockService {

    public boolean isNotAuthenticated() {
        return true;
    }
}
