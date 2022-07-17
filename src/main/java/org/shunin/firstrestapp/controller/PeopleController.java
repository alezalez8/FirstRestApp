package org.shunin.firstrestapp.controller;


import org.shunin.firstrestapp.models.Person;
import org.shunin.firstrestapp.services.PeopleService;
import org.shunin.firstrestapp.util.PersonErrorResponse;
import org.shunin.firstrestapp.util.PersonNotCreatedException;
import org.shunin.firstrestapp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationBindHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController    // @Controller + @ResponseBody над каждым методом, этот класс оперирует не моделями, а данными.
@RequestMapping("/people")  // Аннотация @ResponseBody ставится на методы, которые работают с данными, а не с моделями.
public class PeopleController { // т.е. @ResponseBody отдает java-объекты клиенту в виде JSON'a

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


    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors
            ) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
        peopleService.save(person);
        // отправляем НТТР ответ с пустым телом и со статусом 200
        return ResponseEntity.ok(HttpStatus.OK);

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

    @ExceptionHandler // этот метод ловит исключения и который возвращает необходимый объект
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                ,
                System.currentTimeMillis()
        );
        // B HTTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
