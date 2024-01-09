package com.ksilisk.leetty.web.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
