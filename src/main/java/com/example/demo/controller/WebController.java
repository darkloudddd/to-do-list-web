package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;

@Controller
public class WebController {
    private final TaskService taskService;

    public WebController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "priority") String sortBy) {
        model.addAttribute("tasks", taskService.getAllTasks(sortBy));
        model.addAttribute("newTask", new Task());
        return "index";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task newTask, Model model) {
        if (taskService.existsByDescription(newTask.getDescription())) {
            model.addAttribute("alreadyExist", true);
            model.addAttribute("tasks", taskService.getAllTasks("priority"));
            model.addAttribute("newTask", new Task());
            return "index";
        }
        taskService.addTask(newTask);
        return "redirect:/";
    }

    @PostMapping("/complete/{id}")
    public String completeTask(@PathVariable Long id) {
        taskService.completeTask(id);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.removeTask(id);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchTasks(@RequestParam String keyword, Model model) {
        var results = taskService.searchTasks(keyword);
        model.addAttribute("newTask", new Task());
        model.addAttribute("keyword", keyword);
        if (results.isEmpty()) {
            model.addAttribute("noResults", true);
            // Show all tasks if no results
            model.addAttribute("tasks", taskService.getAllTasks("priority"));
        } else {
            model.addAttribute("tasks", results);
        }
        return "index";
    }
}