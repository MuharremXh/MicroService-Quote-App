package com.rremimicroservices.userservice.exceptions;

public class UserNotFoundException extends RuntimeException {
    private Long id;
    public UserNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
