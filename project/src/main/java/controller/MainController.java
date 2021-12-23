package controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping
    public String helloWorld(){
        return "OpenPick Web Application";
    }

    @RequestMapping("/chat")
    public String chat(){
        return "OpenPick Chat";
    }

    @RequestMapping("/login")
    public String login(){
        return "OpenPick Login";
    }

    @RequestMapping("/myprojects")
    public String myprojects(){
        return "OpenPick MyProjects";
    }

    @RequestMapping("/openprojects")
    public String openprojects(){
        return "OpenPick Open Projects";
    }

    @RequestMapping("/task")
    public String task(){
        return "OpenPick Task Management";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String printHello(ModelMap model) {
        model.addAttribute("message", "Hello Spring MVC Framework!");
        return "hello";
    }
}
