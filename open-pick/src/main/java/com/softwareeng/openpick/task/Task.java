package com.softwareeng.openpick.task;

import com.softwareeng.openpick.project.Project;
import com.softwareeng.openpick.user.User;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tasks")
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "title", length = 25)
    private String title;

    @Column(nullable = false, name = "start_date")
    private Date startDate;

    @Column(nullable = false, name = "deadline")
    private Date deadline;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "assigner_id", referencedColumnName = "id")
    private User assigner;

    @ManyToOne
    @JoinColumn(name = "assignee_id", referencedColumnName = "id")
    private User assignee;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public User getAssigner() {
        return assigner;
    }

    public void setAssigner(User assigner) {
        this.assigner = assigner;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
