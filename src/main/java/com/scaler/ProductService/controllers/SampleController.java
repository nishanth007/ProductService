package com.scaler.ProductService.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/say")
public class SampleController {

    @GetMapping("/hello/{name}")
    @ResponseBody
    public String sayHello(@PathVariable("name")  String name){
        return "Hello " + name + "gilebi";
    }

    @GetMapping("/bye")
    public String sayBye(){
        return "Hello";
    }
}
