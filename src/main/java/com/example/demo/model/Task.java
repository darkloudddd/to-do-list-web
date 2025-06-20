package com.example.demo.model;

import java.time.LocalDate;
import java.util.Objects;

public class Task {
    private Long id;
    private String description;
    private int priority; // 1=High, 2=Medium, 3=Low
    private LocalDate dueDate;
    private boolean completed;
    private String username;

    // Constructors, getters, setters
    public Task() {
    }
    public Task(Long id, String description, int priority, LocalDate dueDate, boolean completed) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = completed;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", completed=" + completed +
                ", username='" + username + '\'' +
                '}';
    }
    // Optionally, you can generate equals() and hashCode() methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return priority == task.priority &&
                completed == task.completed &&
                Objects.equals(id, task.id) &&
                Objects.equals(description, task.description) &&
                Objects.equals(dueDate, task.dueDate) &&
                Objects.equals(username, task.username);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, description, priority, dueDate, completed, username);
    }
    
    // ...generate with IDE or Lombok...
    public static TaskBuilder builder() {
        return new TaskBuilder();
    }
    public static class TaskBuilder {
        private Long id;
        private String description;
        private int priority;
        private LocalDate dueDate;
        private boolean completed;
        private String username;

        public TaskBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public TaskBuilder description(String description) {
            this.description = description;
            return this;
        }
        public TaskBuilder priority(int priority) {
            this.priority = priority;
            return this;
        }
        public TaskBuilder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }
        public TaskBuilder completed(boolean completed) {
            this.completed = completed;
            return this;
        }
        public TaskBuilder username(String username) {
            this.username = username;
            return this;
        }
        public Task build() {
            return new Task(id, description, priority, dueDate, completed);
        }
    }
}