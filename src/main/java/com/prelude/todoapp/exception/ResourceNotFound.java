package com.prelude.todoapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFound extends RuntimeException {
    private final HttpStatus status;

    public ResourceNotFound(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
