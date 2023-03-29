package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Task;

import java.util.List;

public interface HistoryManager {
    public boolean add(Task task);

    public List<Task> getHistory();
}
