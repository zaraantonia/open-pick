package com.softwareeng.openpick.project;

import com.softwareeng.openpick.OpenPickApplication;
import com.softwareeng.openpick.user.NotFoundException;
import com.softwareeng.openpick.user.User;
import com.softwareeng.openpick.user.UserNotFoundException;
import com.softwareeng.openpick.user.UserService;
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

    @Autowired
    private UserService userService;

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

    @GetMapping("/users/{user_id}/projects/{project_id}")
    public String showProjectsFromUser(@PathVariable("user_id") Integer userId, @PathVariable("project_id") Integer projectId, Model model, RedirectAttributes ra){
        try{
            Project project = service.get(projectId);
            model.addAttribute("currentProject",project);
            if(project.getTitle() == null) throw new NotFoundException("ew");
            model.addAttribute("currentUser", userService.get(userId));
            return "project_view";
        }
        catch (NotFoundException | UserNotFoundException e) {
            return "/users/{user_id}";
        }
    }

    @GetMapping("/users/{user_id}/projects/new")
    public String addNewProjectToUser(){
 //       try {
//            model.addAttribute("currentUser", userService.get(userId));
//            model.addAttribute("project", new Project());
//            model.addAttribute("pageTitle", "Add New User");
            return "project_form";
//        } catch (UserNotFoundException e) {
//            return "/users/{user_id}/projects";
//        }
    }

    @PostMapping("/users/{user_id}/projects/save")
    public String saveProject(Project project, RedirectAttributes ra){
        //TODO
        return "redirect:/projects";
    }


}
