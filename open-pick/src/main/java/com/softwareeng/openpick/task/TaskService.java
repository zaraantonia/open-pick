package com.softwareeng.openpick.task;

import com.softwareeng.openpick.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;

    public void save(Task task) {
        repo.save(task);
    }

    public Task get(Integer taskId) throws NotFoundException {
        Optional<Task> result = repo.findById(taskId);
        if (result.isPresent()){
            return result.get();
        }
        throw new NotFoundException("Could not find any tasks with ID " + taskId);
    }

    public void delete(Task task) {
        repo.delete(task);
    }
}
