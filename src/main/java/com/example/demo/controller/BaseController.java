package com.example.demo.controller;

import com.example.demo.form.DistributorForm;
import com.example.demo.form.GeneratorForm;
import com.example.demo.form.PersonForm;
import com.example.demo.logics.Distributor;
import com.example.demo.logics.PersonGenerator;
import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class BaseController {

    @Autowired
    private PersonGenerator personGenerator;
    @Autowired
    private PersonService personService;
    @Autowired
    private Distributor distributor;

    private int groupCount;

    @RequestMapping(value = { "/persons" }, method = RequestMethod.GET)
    public String persons(Model model) {
        model.addAttribute("persons", personService.getAllPersons());
        model.addAttribute("generatorForm",new GeneratorForm());
        model.addAttribute("distributorForm",new DistributorForm());
        return "persons";
    }

    @RequestMapping(value = { "/persons" }, method = RequestMethod.POST)
    public String generatePersons(Model model,@ModelAttribute("generatorForm") GeneratorForm generatorForm) {
        int count = generatorForm.getCount();
        if (count > 0 && count < 1001) {
            personService.deleteAll();
            personGenerator.generate(generatorForm.getCount());
        }else{
            model.addAttribute("errorMessage", "Generate count should be > 0 and < 1001");
            return "persons";
        }
        return "redirect:/persons";
    }

    @RequestMapping(value = { "/persons" }, method = RequestMethod.PUT)
    public String group(Model model,@ModelAttribute("distributorForm") DistributorForm distributorForm) {
        groupCount = distributorForm.getGroupCount();

        return "redirect:/result";
    }

    @RequestMapping(value = { "/result" }, method = RequestMethod.GET)
    public String result(Model model) {
        try {
            List<Person> persons = personService.getAllPersons();
            model.addAttribute("result", distributor.process(persons, groupCount));
            model.addAttribute("average",(float) persons.stream().mapToInt(Person::getScore).sum() / persons.size());
        }catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "result";
    }

    @RequestMapping(value = { "/persons/{id}" }, method = RequestMethod.POST)
    public String savePerson(Model model, @PathVariable("id") int id,
                             @ModelAttribute("personForm") PersonForm personForm) {

        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();
        int score = personForm.getScore();

        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
            if (id == 0) {
                personService.add(new Person(firstName, lastName, score));
            }else if (id > 0){
                personService.save(personService.get(id).withFirstName(firstName).withLastName(lastName));
            }

            return "redirect:/persons";
        }

        return "person";
    }

    @RequestMapping(value = { "/persons/{id}" }, method = RequestMethod.GET)
    public String open(Model model, @PathVariable("id") int id) {
        try {
            Person person;
            if (id != 0) {
                person = personService.get(id);
            }else{
                person = new Person();
            }
            PersonForm personForm = new PersonForm();
            personForm.setFirstName(person.getFirstName());
            personForm.setLastName(person.getLastName());
            personForm.setId(person.getId());
            model.addAttribute("personForm", personForm);
        }catch(Exception e){
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "person";
    }

    @RequestMapping(value = { "/persons/{id}" }, method = RequestMethod.DELETE)
    public String delete(Model model, @PathVariable("id") int id){
        String error = "";
        try {
            if (id > 0) {
                personService.delete(personService.get(id));
                return "redirect:/persons";
            } else {
                error = "No person with this id " + id;
            }
        }catch (Exception e){
            error = e.getMessage();
        }

        model.addAttribute("errorMessage", error);
        return "person";
    }

}
