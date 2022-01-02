package com.softwareeng.openpick;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage(){
        return "index";
    }

    @GetMapping("/about")
    public String showAboutPage(){
        return "about";
    }
}

