package com.example.restservice.components.controllers;

import com.example.restservice.components.dto.Person;
import com.example.restservice.components.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id) {
        System.out.println("...called GET");
        return new ResponseEntity<>(personService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/persons/")
    public ResponseEntity<List<Person>> getAllPersons(@RequestParam(required = false) String firstName) {
        System.out.println("...called GET(all)");
        List<Person> persons;
        if (firstName != null) {
            persons = personService.getPersonsByFirstName(firstName);
        } else {
            persons = personService.findAll();
        }
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Boolean> deletePerson(@PathVariable int id) {
        System.out.println("...called DELETE");
        personService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/persons/")
    public ResponseEntity<Person> addPerson(@Valid @RequestBody Person person) {
        System.out.println("...called POST");
        return new ResponseEntity<>(personService.add(person), HttpStatus.CREATED);
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Integer id, @Valid @RequestBody Person person) {
        System.out.println("...called PUT");
        return new ResponseEntity<>(personService.update(id, person), HttpStatus.OK);
    }

    @GetMapping("/persons/countAll")
    public ResponseEntity<Integer> countAllPersons() {
        System.out.println("...called countAllPersons");
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE).body(personService.count());
    }
}
