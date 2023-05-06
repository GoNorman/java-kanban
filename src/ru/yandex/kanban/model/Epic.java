package ru.yandex.kanban.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private ArrayList<Subtask> subtaskList = new ArrayList<>();

    public Epic(String newName, String newDescription) {
        super(newName, newDescription);
    }

    public List<Subtask> getSubtaskList() {
        return subtaskList;
    }

    @Override
    public String toString() {
        return "type=epic" + ",id=" + getId() +",name="+ getName() +",description="+
                getDescription() + ",status=" + getStatus();
    }
}
