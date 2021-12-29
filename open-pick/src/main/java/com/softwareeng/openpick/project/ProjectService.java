package com.softwareeng.openpick.project;

import com.softwareeng.openpick.user.NotFoundException;
import com.softwareeng.openpick.user.User;
import com.softwareeng.openpick.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repo;


    public List<Project> getByUserId(Integer id) {
        List<Project> projects = (List)repo.findAllById(repo.findProjectsIdByUserId(id));
        return projects;
    }

    public Project get(Integer projectId) throws NotFoundException {
        Optional<Project> result = repo.findById(projectId);
        if (result.isPresent()){
            return result.get();
        }
        throw new NotFoundException("Could not find any projects with ID " + projectId);
    }

    public void updateUserIdInUsersProjects(Integer oldId, Integer newId) {
        repo.updateUserId(oldId, newId);
    }

    public void updateUserIdInProject(Integer oldId, Integer newId) {
        repo.updateUserIdInProject(oldId,newId);
    }
}
