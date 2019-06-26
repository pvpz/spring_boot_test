package com.example.demo.logics;

import com.example.demo.model.Person;

import java.util.List;

public class Group {
    private List<Person> persons;
    private int sum;

    public Group(List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void calculateSum(){
        sum = persons.stream().mapToInt(Person::getScore).sum();
    }
}
