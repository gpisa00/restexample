package it.arteprogrammazione.restexample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonsController {

    @RequestMapping("/")
    public String greeting() {
        return "redirect:/swagger-ui.html";
    }
}
