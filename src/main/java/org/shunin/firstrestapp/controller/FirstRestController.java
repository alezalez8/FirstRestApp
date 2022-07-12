package org.shunin.firstrestapp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // @Controller + @ResponseBody над каждым методом
@RequestMapping("/api")
public class FirstRestController {

   // @ResponseBody
    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello world!";
    }






}
