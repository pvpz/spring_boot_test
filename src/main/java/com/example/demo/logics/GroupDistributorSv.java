package com.example.demo.logics;

import com.example.demo.DemoApplication;
import com.example.demo.model.Person;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        int numbersInGroup = row.size() / groupCount;
        int numbersInGroupMax = (int) Math.ceil((double)row.size() / groupCount);
        row.sort(Comparator.comparing(Person::getScore));
        Collections.reverse(row);
        if (DemoApplication.DEBUG){
            row.forEach(System.out::println);
        }

        List<Group> result = new ArrayList<>();
        for (int i = 0; i < groupCount; i++){
            result.add(new Group(new ArrayList<>()));
        }
        boolean reverse = false;
        for (int i = 0; i < numbersInGroupMax; i++) {
            List<Person> currentList = moveCollectionElements(row, groupCount);
            if (reverse) {
                Collections.reverse(currentList);
            }
            reverse = !reverse;
            for (int j = 0; j < currentList.size(); j++) {
                result.get(j).getPersons().add(currentList.get(j));
            }
        }
        setGroupAverage(result);
        return result;
    }

    private void setGroupAverage(List<Group> result) {
        for (Group group : result) {
            int sum = 0;
            for (Person i : group.getPersons()){
                sum += i.getScore();
            }
            group.setAverage((float)sum / group.getPersons().size());
        }
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

