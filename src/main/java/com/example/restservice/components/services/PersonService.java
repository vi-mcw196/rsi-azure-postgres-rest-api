package com.example.restservice.components.services;

import com.example.restservice.components.dto.Person;

import java.util.List;

public interface PersonService {
    public List<Person> findAll();
    public Person findById(Integer id);
    public Person add(Person person);
    public Person update(Integer id, Person person);
    public Boolean deleteById(Integer id);
    public Integer count();
    List<Person> getPersonsByFirstName(String firstName);

}
