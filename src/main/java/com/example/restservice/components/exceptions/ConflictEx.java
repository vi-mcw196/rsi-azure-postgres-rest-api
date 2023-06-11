package com.example.restservice.components.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT)
public class ConflictEx extends RuntimeException {
    public ConflictEx() {
        super("There is a conflict");
    }
    public ConflictEx(int id) {
        super(String.valueOf(id));
    }
    public ConflictEx(String message) {
        super(message);
    }
}