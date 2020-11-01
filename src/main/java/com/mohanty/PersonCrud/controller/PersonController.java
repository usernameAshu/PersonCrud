package com.mohanty.PersonCrud.controller;

import com.mohanty.PersonCrud.model.Person;
import com.mohanty.PersonCrud.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @PostMapping
    public int addPerson(@RequestBody Person person) {
        LOGGER.info("POST: addPerson called");
        return personService.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPeople() {
        LOGGER.info("GET: getAllPeople called");
        return personService.getAllPeople();
    }

    @GetMapping(path="{id}")
    public Person selectPersonById(@PathVariable("id") UUID id) {
        LOGGER.info("GET: selectPersonById called with ID:",id.toString());
        return personService.selectPersonById(id)
                .orElse(null);
    }

    @DeleteMapping(path="{id}")
    public int deletePersonById(@PathVariable("id") UUID id) {
        LOGGER.info("DELETE: deletePersonById called with ID:",id.toString());
        return personService.deletePersonById(id);
    }

    @PutMapping(path="{id}")
    public int updatePersonById(@PathVariable("id") UUID id, @RequestBody Person person) {
        return personService.updatePersonById(id,person);
    }
}
