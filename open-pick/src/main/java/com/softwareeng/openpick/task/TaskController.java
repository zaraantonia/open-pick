package com.softwareeng.openpick.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping("/projects/{project_id}/tasks")
    public String showTasksOfProject(@PathVariable("project_id") Integer projectId, Model model, RedirectAttributes ra){
        //TODO
        return "/projects/{project_id}/tasks";
    }

    @GetMapping("/projects/{project_id}/tasks/new")
    public String addNewTask(@PathVariable("project_id") Integer projectId, Model model, RedirectAttributes ra){
        //TODO
        return "task_form";
    }

    @GetMapping("/projects/{project_id}/tasks/save")
    public String saveTask(@PathVariable("project_id") Integer projectId, Model model, RedirectAttributes ra){
        //TODO
        return "redirect:/projects/{project_id}/tasks";
    }
}
