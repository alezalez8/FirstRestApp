package org.shunin.firstrestapp.controller;


import org.shunin.firstrestapp.models.Person;
import org.shunin.firstrestapp.services.PeopleService;
import org.shunin.firstrestapp.util.PersonErrorResponse;
import org.shunin.firstrestapp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController    // @Controller + @ResponseBody над каждым методом, этот класс оперирует не моделями, а данными.
@RequestMapping("/people")  // Аннотация @ResponseBody ставится на методы, которые работают с данными, а не с моделями.
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public List<Person> findAll() {
        return peopleService.findAll();  // Jackson конвертирует все эти объекты в JSON
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") int id) {
        return peopleService.findOne(id); // Jackson конвертирует в JSON
    }

    @ExceptionHandler // этот метод ловит исключения и который возвращает необходимый объект
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id wasn't found",
                System.currentTimeMillis()
        );
        // B HTTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
