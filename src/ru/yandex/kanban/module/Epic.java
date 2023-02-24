package ru.yandex.kanban.module;

import java.util.HashMap;
public class Epic extends Task{
    private HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
    public void setValueToHashMap(int id, Subtask subtask) {
        subtaskHashMap.put(id, subtask);
    }
    public HashMap<Integer, Subtask> getSubtaskHashMap() {
        return subtaskHashMap;
    }
}
