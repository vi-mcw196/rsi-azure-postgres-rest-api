package com.example.restservice.components.services;

import com.example.restservice.components.dto.Person;
import com.example.restservice.components.exceptions.PersonExistsEx;
import com.example.restservice.components.exceptions.PersonNotFoundEx;
import com.example.restservice.components.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(personRepository.findAll());
    }

    @Override
    public Person findById(Integer id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundEx(id));
    }

    @Override
    public Person add(Person person) {
        Optional<Person> personExists = Optional.ofNullable(personRepository.findByEmail(person.getEmail()));

        if(personExists.isPresent()){
            throw new PersonExistsEx(person.getId());
        }

        person.setId(0);
        return personRepository.save(person);
    }

    @Override
    public Person update(Integer id, Person person) {
        if (!personRepository.existsById(id))
            throw new PersonNotFoundEx(id);
        person.setId(id);
        return personRepository.save(person);
    }

    @Override
    public Boolean deleteById(Integer id) {
        if (!personRepository.existsById(id))
            throw new PersonNotFoundEx(id);
        personRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Integer count() {
        ArrayList<Person> personList = new ArrayList<>(personRepository.findAll());
        return personList.size();
    }

    @Override
    public List<Person> getPersonsByFirstName(String firstName) {
        return new ArrayList<>(personRepository.findByFirstName(firstName));
    }
}
