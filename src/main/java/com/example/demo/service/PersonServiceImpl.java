package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public void add(Person person) {
        personRepository.save(person);
    }

    @Override
    public void remove(Person person) {
        personRepository.delete(person);
    }

    @Override
    public Person get(long id) {
        return personRepository.findAll().stream()
                .filter(l -> l.getId() == id).findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }
}
