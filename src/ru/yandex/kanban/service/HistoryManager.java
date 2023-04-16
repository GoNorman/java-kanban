package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Task;

import java.util.List;

public interface HistoryManager {
     boolean add(Task task);
     void remove(int id);
     List<Task> getHistory();
}
