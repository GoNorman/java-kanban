package ru.yandex.kanban.model;

import java.util.List;

public interface HistoryManager {
    public boolean add(Task task);

    public List<Task> getHistory();
}
