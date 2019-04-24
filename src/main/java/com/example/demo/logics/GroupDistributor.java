package com.example.demo.logics;

import com.example.demo.DemoApplication;
import com.example.demo.model.Person;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//@Component
public class GroupDistributor implements Distributor {

    @Override
    public List<Group> process(List<Person> row, int groupCount) throws IOException {
        if (row == null || row.size() < 1){
            throw new IOException("No DB elements");
        }

        if (groupCount < 1) {
            throw new IOException("Group count must be not less then 1");
        }

        if (row.size() % groupCount != 0){
            throw new IOException("Количество чисел в ряду не кратно количеству групп.");
        }

        int numbersInGroup = row.size() / groupCount;
        row.sort(Comparator.comparing(Person::getScore));
        if (DemoApplication.DEBUG){
            row.forEach(System.out::println);
        }

        float average = (float) row.stream().mapToInt(Person::getScore).sum() / groupCount;

        List<Group> result = new ArrayList<>();
        for (int i = 0; i < groupCount; i++){
            result.add(new Group(new ArrayList<>()));
            List<Person> currentList = result.get(i).getPersons();
            for(int j = row.size() - 1; currentList.size() < numbersInGroup; j--){
                if (j < numbersInGroup - currentList.size()){
                    currentList.add(row.get(j));
                    row.remove(j);
                    continue;
                }
                if (currentList.stream().mapToInt(Person::getScore).sum() + row.get(j).getScore() <= average){
                    currentList.add(row.get(j));
                    row.remove(j);
                }
            }
            result.get(i).setAverage((float)currentList.stream().mapToInt(Person::getScore).sum() / currentList.size());
        }
        return result;
    }
}

