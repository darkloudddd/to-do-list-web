package com.example.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.model.Task;

@Service
public class TaskService {
    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public Task addTask(Task task) {
        long id = idCounter.incrementAndGet();
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    public List<Task> getAllTasks(String sortBy) {
        Comparator<Task> comparator = Comparator
            .comparing(Task::isCompleted) // false (not completed) comes before true (completed)
            .thenComparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))
            .thenComparing(Task::getPriority);

        return tasks.values().stream()
            .sorted(comparator)
            .collect(Collectors.toList());
    }

    public boolean completeTask(Long id) {
        Task task = tasks.get(id);
        if (task != null) {
            task.setCompleted(true);
            return true;
        }
        return false;
    }

    public boolean removeTask(Long id) {
        return tasks.remove(id) != null;
    }

    public List<Task> searchTasks(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return new ArrayList<>(tasks.values());
        }
        String lowerKeyword = keyword.toLowerCase();
        return tasks.values().stream()
                .filter(task -> task.getDescription() != null && task.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    public boolean existsByDescription(String description) {
        return tasks.values().stream()
                .anyMatch(task -> task.getDescription().equalsIgnoreCase(description));
    }

    public void editTask(Long id, String description, Integer priority, String dueDate) {
        Task task = tasks.get(id);
        if (task != null) {
            task.setDescription(description);
            task.setPriority(priority);
            task.setDueDate(dueDate == null || dueDate.isEmpty() ? null : java.time.LocalDate.parse(dueDate));
        }
    }

    public List<Task> getFilteredTasks(Integer priority, Boolean completed, String dueDate) {
        return tasks.values().stream()
            .filter(t -> priority == null || t.getPriority() == priority)
            .filter(t -> completed == null || t.isCompleted() == completed)
            .filter(t -> dueDate == null || dueDate.isEmpty() || (t.getDueDate() != null && t.getDueDate().equals(dueDate)))
            .sorted(Comparator
                .comparing(Task::isCompleted)
                .thenComparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(Task::getPriority))
            .collect(Collectors.toList());
    }
}