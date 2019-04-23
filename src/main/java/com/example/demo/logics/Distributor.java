package com.example.demo.logics;

import com.example.demo.model.Person;

import java.io.IOException;
import java.util.List;

public interface Distributor {

    public List<Group> process(List<Person> row, int groupCount) throws IOException;

}
