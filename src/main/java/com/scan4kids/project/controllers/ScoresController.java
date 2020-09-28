package com.scan4kids.project.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScoresController {

    @GetMapping("/score")
    public String showScoreForm() {
        return "scores/scowes";
    }


}
