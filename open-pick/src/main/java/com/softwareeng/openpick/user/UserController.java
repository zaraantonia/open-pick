package com.softwareeng.openpick.user;


import com.softwareeng.openpick.exception.NotFoundException;
import com.softwareeng.openpick.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/users")
    public String showUserList(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            User loggedInUser = service.findByUsername(((UserDetails)principal).getUsername());
            model.addAttribute("loggedInUserId", loggedInUser.getId().toString());
            System.out.println(loggedInUser);
        } else {
            model.addAttribute("loggedInUserId", "");
        }

        return "users";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Add New User");
        model.addAttribute("oldId", 0);
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra) throws UserNotFoundException {
        user.setRole("USER");
        service.save(user);
        return "redirect:/users/{oldId}";
    }

    @RequestMapping(value = "/users/save/{oldId}", method = RequestMethod.POST)
    public String saveUserAgain(@PathVariable("oldId") String oldId, Model model, User user, RedirectAttributes ra) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setId(Integer.parseInt(oldId));
        user.setRole("USER");
        service.save(user);

        ra.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/users/{oldId}";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer userId, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(userId);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User (ID: " + userId + ")");
            model.addAttribute("oldId", userId.toString());
            return "user_form";
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", "The .");
            return "redirect:/projects";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message","User has been deleted succesfully");
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("USER");
        user.setEnabled(true);
        service.save(user);

        return "register_success";
    }

    @GetMapping("/users/{id}")
    public String viewProfileOfUser(Model model, @PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            User currentUser = service.get(id);
            model.addAttribute("currentUser", currentUser);
            ra.addFlashAttribute("message","User has been deleted succesfully");
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "profile";
    }

}
