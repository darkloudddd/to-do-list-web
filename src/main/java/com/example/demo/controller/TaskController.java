package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task addTask(@RequestBody Task task, @RequestParam String user) {
        return taskService.addTask(task, user);
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
