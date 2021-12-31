package com.softwareeng.openpick.task;

import com.softwareeng.openpick.exception.NotFoundException;
import com.softwareeng.openpick.project.ProjectService;
import com.softwareeng.openpick.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/users/{user_id}/projects/{project_id}/newtask")
    public String addTask(@PathVariable("user_id") Integer userId, @PathVariable("project_id") Integer projectId, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("task", new Task());
            model.addAttribute("currentUser", userService.get(userId));
            model.addAttribute("currentProject", projectService.get(projectId));
            model.addAttribute("pageTitle", "Add Task");
        } catch (NotFoundException e) {
            return "redirect:/users/{user_id}/projects/{project_id}";
        }

        ra.addFlashAttribute("message", "User has been deleted succesfully");
        return "task_form";
    }

    @RequestMapping("/users/{user_id}/projects/{project_id}/savetask")
    public String saveTask(@PathVariable("user_id") Integer userId, @PathVariable("project_id") Integer projectId, Task task, RedirectAttributes ra) {
        try {
            task.setProject(projectService.get(projectId));
            taskService.save(task);
            ra.addFlashAttribute("message", "The task has been saved successfully.");
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", "The task saved unsuccessfully.");
        }
        return "redirect:/users/{user_id}/projects/{project_id}";
    }

    @GetMapping("/users/{user_id}/projects/{project_id}/deletetask/{task_id}")
    public String deleteTask(@PathVariable("task_id") Integer taskId, RedirectAttributes ra) {
        try {
            taskService.delete(taskService.get(taskId));
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", "The task deleted unsuccessfully.");
        }
        return "redirect:/users/{user_id}/projects/{project_id}";
    }

    @GetMapping("/users/{user_id}/projects/{project_id}/edittask/{task_id}")
    public String showEditFormTask(@PathVariable("user_id") Integer userId, @PathVariable("project_id") Integer projectId,@PathVariable("task_id") Integer taskId, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("task", taskService.get(taskId));
            model.addAttribute("currentUser", userService.get(userId));
            model.addAttribute("currentProject", projectService.get(projectId));
            model.addAttribute("pageTitle", "Edit Task");
            return "task_form_edit";
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", "The task deleted unsuccessfully.");
        }
        return "redirect:/users/{user_id}/projects/{project_id}";
    }

    @RequestMapping("/users/{user_id}/projects/{project_id}/savetask/{task_id}")
    public String editTask(@PathVariable("task_id") Integer taskId, @PathVariable("project_id") Integer projectId, Task task, RedirectAttributes ra) {
        try {
            task.setId(taskId);
            task.setProject(projectService.get(projectId));
            taskService.save(task);
            ra.addFlashAttribute("message", "The task has been modified successfully.");
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", "The task has been modified unsuccessfully.");
        }
        return "redirect:/users/{user_id}/projects/{project_id}";
    }

}
