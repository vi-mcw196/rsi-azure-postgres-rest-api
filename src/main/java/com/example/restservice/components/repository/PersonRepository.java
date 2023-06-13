package com.example.restservice.components.repository;

import com.example.restservice.components.dto.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByEmail(String email);
    List<Person> findByFirstName(String firstName);
}
