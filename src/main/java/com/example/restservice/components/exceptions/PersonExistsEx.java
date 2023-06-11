package com.example.restservice.components.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.ALREADY_REPORTED)
public class PersonExistsEx extends RuntimeException {
    public PersonExistsEx() {
        super("This person already exists");
    }
    public PersonExistsEx(int id) {
        super(String.valueOf(id));
    }

}