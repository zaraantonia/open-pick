package com.softwareeng.openpick.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService service;

    //you can only add projects from the my projects menu,
    //not from the all projects menu

    //same with tasks, from myprojects only

    @GetMapping("/projects")
    public String showAllProjects(Model model){
        //TODO page that will display all the projects
        return "projects";
    }

    @GetMapping("/projects/new")
    public String showNewProjectForm(Model model){
        //TODO new project form
        return "project_form";
    }

    @GetMapping("/projects/{id}")
    public String showProject(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        //TODO
        return "/projects/{id}";
    }

    @GetMapping("/users/{user_id}/projects/")
    public String showProjectsFromUser(@PathVariable("user_id") Integer id, Model model, RedirectAttributes ra){
        //TODO
        return "/users/{user_id}/projects/";
    }

    //@GetMapping("/users/{user_id}/projects/")

    @PostMapping("/users/{user_id}/projects/save")
    public String saveProject(Project project, RedirectAttributes ra){
        //TODO
        return "redirect:/projects";
    }


}
