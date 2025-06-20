package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {
    private final TaskService taskService;

    public WebController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String index(
        HttpSession session, 
        Model model
    ) {
        String username = (String) session.getAttribute("username");
        List<Task> tasks = taskService.getTasksByUsername(username);
        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task());
        return "index";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task newTask, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (taskService.existsByDescription(newTask.getDescription())) {
            model.addAttribute("alreadyExist", true);
            model.addAttribute("tasks", taskService.getTasksByUsername(username));
            model.addAttribute("newTask", new Task());
            return "index";
        }
        taskService.addTask(newTask, username);
        return "redirect:/tasks";
    }

    @PostMapping("/complete/{id}")
    public String completeTask(@PathVariable Long id) {
        taskService.completeTask(id);
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.removeTask(id);
        return "redirect:/tasks";
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

    @PostMapping("/edit")
    public String editTask(@RequestParam Long id,
                       @RequestParam String description,
                       @RequestParam Integer priority,
                       @RequestParam(required = false) String dueDate) {
        taskService.editTask(id, description, priority, dueDate);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks-ui")
    public String tasksUi(
        @RequestParam(required = false) Integer priorityFilter,
        @RequestParam(required = false) Boolean completedFilter,
        @RequestParam(required = false) String dueDateFilter,
        Model model
    ) {
        List<Task> tasks = taskService.getFilteredTasks(priorityFilter, completedFilter, dueDateFilter);
        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task());
        model.addAttribute("priorityFilter", priorityFilter);
        model.addAttribute("completedFilter", completedFilter);
        model.addAttribute("dueDateFilter", dueDateFilter);
        return "index";
    }
}