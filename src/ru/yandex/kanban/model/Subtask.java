package ru.yandex.kanban.model;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String newName, String newDescription, int epicId) {
        super(newName, newDescription);
        this.epicId = epicId;
    }
    public int getEpicId() {
        return epicId;
    }
}
