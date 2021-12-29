package com.softwareeng.openpick.user;


import com.softwareeng.openpick.project.Project;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(length = 100, nullable = false) //name is default here
    private String password;

    @Column(length = 45, nullable = false)
    private  String username;

    @Column(length=12,nullable = false)
    private String role;

    private boolean enabled;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        if(this.projects == null){
            this.projects = projects;
        }
        else{
            this.projects.clear();
            this.projects.addAll(projects);
        }
    }
}
