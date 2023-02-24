package ru.yandex.kanban.module;

public class Subtask extends Task {
    private int epicId;
    public void setEpicId(int id) {
        this.epicId = id;
    }
    public int getEpicId() {
        return epicId;
    }
}
