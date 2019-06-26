package com.example.demo.logics;

import com.example.demo.model.Person;
import com.example.demo.model.Role;
import com.example.demo.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class PersonGenerator {

    @Autowired
    private CommonService<Person> personService;
    @Autowired
    private CommonService<Role> roleService;

    private Random random = new Random();

    public void generate(int count) {
        for (int i = 0; i < count; i++){
            personService.add(new Person(generateName(),generateName(),random.nextInt(98) + 1, generateRole(i)));
        }
    }

    private Role generateRole(int i) {
        List<Role> roles = roleService.getAll();
        return roles.get(i % roles.size());
    }

    private String generateName() {
        int letterCount = random.nextInt(5) + 3;
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < letterCount; i++){
            if (i == 0) {
                name.append((char) (random.nextInt(90 - 65) + 65));
            }else{
                name.append((char) (random.nextInt(122 - 97) + 97));
            }
        }
        return name.toString();
    }

}
