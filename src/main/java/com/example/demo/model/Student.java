package com.example.demo.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Student {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String passportNumber;
}
