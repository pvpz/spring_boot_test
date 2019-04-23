package com.example.demo.controller;

import com.example.demo.form.GeneratorForm;
import com.example.demo.form.PersonForm;
import com.example.demo.logics.PersonGenerator;
import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class BaseController {

    @Autowired
    private PersonGenerator personGenerator;
    @Autowired
    private PersonService personService;

    // Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = { "/persons" }, method = RequestMethod.GET)
    public String persons(Model model) {
        model.addAttribute("persons", personService.getAllPersons());
        model.addAttribute("generatorForm",new GeneratorForm());
        return "persons";
    }

    @RequestMapping(value = { "/persons" }, method = RequestMethod.POST)
    public String generatePersons(Model model,@ModelAttribute("generatorForm") GeneratorForm generatorForm) {
        personGenerator.generate(generatorForm.getCount());
        return "redirect:/persons";
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

        model.addAttribute("errorMessage", errorMessage);
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
            model.addAttribute("errorMessage", errorMessage);
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
