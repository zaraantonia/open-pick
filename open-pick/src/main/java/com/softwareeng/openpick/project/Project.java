package com.softwareeng.openpick.project;

import com.softwareeng.openpick.task.Task;
import com.softwareeng.openpick.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 25)
    private String title;

    @Column(nullable = false, length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    public Project(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.owner = user;
    }

    public Project() {

    }

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Task> tasks = new ArrayList<>();



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner.getUsername() +
                '}';
    }
}
