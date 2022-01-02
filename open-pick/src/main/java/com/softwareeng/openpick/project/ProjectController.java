package com.softwareeng.openpick.project;

import com.softwareeng.openpick.exception.NotFoundException;
import com.softwareeng.openpick.user.User;
import com.softwareeng.openpick.user.UserNotFoundException;
import com.softwareeng.openpick.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService service;

    @Autowired
    private UserService userService;

    @GetMapping("/projects")
    public String showAllProjects(Model model){
        List<Project> allProjects = service.findAll();
        model.addAttribute("allProjects", allProjects);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {

            User loggedInUser = userService.findByUsername(((UserDetails)principal).getUsername());
            model.addAttribute("loggedInUserId", loggedInUser.getId().toString());

            if(loggedInUser.getRole().equals("ADMIN")){
                return "all_projects_admin";
            }

        } else {
            model.addAttribute("loggedInUserId", "");
        }
        return "all_projects";
    }


    @GetMapping("/users/{user_id}/projects/{project_id}")
    public String showProjectsFromUser(@PathVariable("user_id") Integer userId, @PathVariable("project_id") Integer projectId, Model model, RedirectAttributes ra){
        try{
            Project project = service.get(projectId);
            model.addAttribute("currentProject",project);
            if(project.getTitle() == null) throw new NotFoundException("ew");
            model.addAttribute("currentUser", userService.get(userId));

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                User loggedInUser = userService.findByUsername(((UserDetails)principal).getUsername());
                model.addAttribute("loggedInUserId", loggedInUser.getId().toString());

                if(userId.equals(loggedInUser.getId())){
                    return "project_view_owner";
                }
                else{
                    return "project_view_other";
                }

            } else {
                model.addAttribute("loggedInUserId", "");
                return "project_view_other";
            }
        }
        catch (NotFoundException e) {
            return "/users/{user_id}";
        }
    }

    @GetMapping("/users/{user_id}/projects/new")
    public String addNewProjectToUser(@PathVariable("user_id") Integer userId, Model model){
        try {
            User currentUser = userService.get(userId);
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("projectt", new Project());
            return "project_form";
        } catch (NotFoundException e) {
            return "/users/{user_id}";
        }
    }

    @GetMapping("/users/{user_id}/projects/{project_id}/edit")
    public String showEditFormProject(@PathVariable("user_id") Integer userId, @PathVariable("project_id") Integer projectId, Model model, RedirectAttributes ra) {
        try {
            Project project = service.get(projectId);
            model.addAttribute("ourProject", project);
            model.addAttribute("pageTitle", "Edit Project");
            model.addAttribute("userId", userId.toString());
            model.addAttribute("projectId", projectId.toString());
            return "project_form_edit";
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", "The .");
            return "redirect:/users";
        }
    }

    @RequestMapping("/users/{user_id}/projects/save")
    public String saveProject(@PathVariable("user_id") Integer userId, Project ourProject, RedirectAttributes ra, Model model) throws UserNotFoundException, NotFoundException {
        ourProject.setOwner(userService.get(userId));
        service.save(ourProject);

        ra.addFlashAttribute("message", "The project has been saved successfully.");
        return "redirect:/users/{user_id}";
    }

    @RequestMapping("/users/{user_id}/projects/{project_id}/save")
    public String saveProject(@PathVariable("user_id") Integer userId, @PathVariable("project_id") Integer projectId, Project ourProject, RedirectAttributes ra, Model model) throws UserNotFoundException, NotFoundException {
        ourProject.setOwner(userService.get(userId));
        service.save(ourProject);

        ra.addFlashAttribute("message", "The project has been saved successfully.");
        return "redirect:/users/{user_id}";
    }

    @GetMapping("/users/{user_id}/projects/{project_id}/delete")
    public String deleteProject(Model model, @PathVariable("user_id") Integer userId, @PathVariable("project_id") Integer projectId, RedirectAttributes ra) {
        service.delete(projectId);

        ra.addFlashAttribute("message","Project has been deleted succesfully");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {

            User loggedInUser = userService.findByUsername(((UserDetails)principal).getUsername());
            model.addAttribute("loggedInUserId", loggedInUser.getId().toString());

            if(loggedInUser.getRole().equals("ADMIN")){
                return "redirect:/projects";
            }
        } else {
            model.addAttribute("loggedInUserId", "");
        }
        return "redirect:/users/{user_id}";
    }

}
