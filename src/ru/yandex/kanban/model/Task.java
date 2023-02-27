package ru.yandex.kanban.model;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected String status;

    public Task(String newName, String newDescription) {
        this.name = newName;
        this.description = newDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
