package com.example.ad_personalization_engine.ad_personalization_engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthEngineController {

    @GetMapping("/health")
    public String health(){
        return "Ok";
    }
}

