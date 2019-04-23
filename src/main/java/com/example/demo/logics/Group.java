package com.example.demo.logics;

import com.example.demo.model.Person;

import java.util.List;

public class Group {
    private List<Person> persons;
    private float average;

    public Group(List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
