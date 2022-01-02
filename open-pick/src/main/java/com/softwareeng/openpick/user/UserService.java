package com.softwareeng.openpick.user;

import com.softwareeng.openpick.exception.NotFoundException;
import com.softwareeng.openpick.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User get(Integer id) throws NotFoundException {
        Optional<User> result = repo.findById(id);
        if (result.isPresent()) {
            User currentUser = result.get();
            return currentUser;
        }
        throw new NotFoundException("Could not find any users with ID " + id);
    }

    public void delete(Integer id) throws NotFoundException {
        Long count = repo.countById(id);
        if(count == null | count == 0){
            throw new NotFoundException("Could not find user.");
        }
        repo.deleteById(id);
    }

    public User findByUsername(String username) {
        return repo.getUserByUsername(username);
    }
}
