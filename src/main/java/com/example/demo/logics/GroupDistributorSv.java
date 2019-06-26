package com.example.demo.logics;

import com.example.demo.DemoApplication;
import com.example.demo.model.Person;
import com.example.demo.model.Role;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GroupDistributorSv implements Distributor {

    @Override
    public List<Group> process(List<Person> row, int groupCount) throws IOException {
        if (row == null || row.size() < 1){
            throw new IOException("No DB elements");
        }

        if (groupCount < 1) {
            throw new IOException("Group count must be not less then 1");
        }

        List<List<Person>> personLists = separatePersonsByRoles(row);
        sortByScore(personLists);
        if (DemoApplication.DEBUG){
            personLists.forEach(p -> p.forEach(System.out::println));
        }

        List<Group> result = new ArrayList<>();
        for (int i = 0; i < groupCount; i++){
            result.add(new Group(new ArrayList<>()));
        }
        for (List<Person> personList : personLists) {
            boolean reverse = false;
            result.forEach(Group::calculateSum);
            result.sort(Comparator.comparing(Group::getSum));
            int numbersInGroupMax = (int) Math.ceil((double)personList.size() / groupCount);
            for (int i = 0; i < numbersInGroupMax; i++) {
                List<Person> currentList = moveCollectionElements(personList, groupCount);
                if (reverse) {
                    Collections.reverse(currentList);
                }
                reverse = !reverse;
                for (int j = 0; j < currentList.size(); j++) {
                    result.get(j).getPersons().add(currentList.get(j));
                }
            }
        }
        result.forEach(Group::calculateSum);
        return result;
    }

    private void sortByScore(List<List<Person>> personLists) {
        for (List<Person> personList : personLists){
            personList.sort(Comparator.comparing(Person::getScore));
            Collections.reverse(personList);
        }
    }

    private List<List<Person>> separatePersonsByRoles(List<Person> row) {
        List<List<Person>> personLists = new ArrayList<>();
        Set<Role> roles = row.stream().map(Person::getRole).collect(Collectors.toSet());
        for (Role role : roles) {
            personLists.add(row.stream()
                    .filter(p -> p.getRole().getId() == role.getId()).collect(Collectors.toList()));
        }
        return personLists;
    }

    private List<Person> moveCollectionElements(List<Person> row, int groupCount) {
        List<Person> currentList = new ArrayList<>();
        groupCount = (row.size() < groupCount ? row.size() : groupCount);
        for (int i = 0; i < groupCount; i++){
            currentList.add(row.get(0));
            row.remove(0);
        }
        return currentList;
    }


}

