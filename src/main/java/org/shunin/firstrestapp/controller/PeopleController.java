package org.shunin.firstrestapp.controller;


import org.shunin.firstrestapp.models.Person;
import org.shunin.firstrestapp.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public List<Person> findAll() {
        return peopleService.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") int id) {
        return peopleService.findById(id);
    }
}
