package com.example.demo.service;

import com.example.demo.model.Person;

import java.util.List;

public interface PersonService {

    public List<Person> getAllPersons();

    public void add(Person person);

    public void remove(Person person);
}
