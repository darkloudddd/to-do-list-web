package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam(defaultValue = "priority") String sortBy) {
        return taskService.getAllTasks(sortBy);
    }

    @PutMapping("/{id}/complete")
    public void completeTask(@PathVariable Long id) {
        taskService.completeTask(id);
    }

    @DeleteMapping("/{id}")
    public void removeTask(@PathVariable Long id) {
        taskService.removeTask(id);
    }
}
