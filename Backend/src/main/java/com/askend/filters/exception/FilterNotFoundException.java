package com.askend.filters.exception;

public class FilterNotFoundException extends RuntimeException{
    public FilterNotFoundException(Long id) {
        super("Filter not found with id: " + id);
    }
}
