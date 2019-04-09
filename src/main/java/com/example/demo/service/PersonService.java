package com.example.demo.service;

import com.example.demo.model.Person;

import java.util.List;

public interface PersonService {

    public List<Person> getAllPersons();

    public void add(Person person);

    public void delete(Person person);

    public Person get(long id);

    public void save(Person person);
}
