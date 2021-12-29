package com.softwareeng.openpick.user;

import com.softwareeng.openpick.project.Project;
import com.softwareeng.openpick.project.ProjectRepository;
import com.softwareeng.openpick.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private ProjectService projectService;

    public List<User> listAll(){
        return (List<User>) repo.findAll();
    }

    public void save(User user){
        repo.save(user);
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id);
        if (result.isPresent()) {
            User currentUser = result.get();
            //List<Project> currentProjects =  projectService.getByUserId(currentUser.getId());
            //currentUser.setProjects(currentProjects);
            return currentUser;
        }
        throw new UserNotFoundException("Could not find any users with ID " + id);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if(count == null | count == 0){
            throw new UserNotFoundException("Could not find user.");
        }
        repo.deleteById(id);
    }
    
}
