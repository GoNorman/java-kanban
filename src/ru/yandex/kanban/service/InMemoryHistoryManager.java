package ru.yandex.kanban.service;

import ru.yandex.kanban.model.HistoryManager;
import ru.yandex.kanban.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history = new ArrayList<>();
    @Override
    public boolean add(Task task) {
        if (task == null) {
            return false;
        }
        if (getHistory().size() >= 10) {
            getHistory().remove(0);
            for (int i = 1; i <= getHistory().size(); i++) {
                history.add(task);
            }
        } else {
            history.add(task);
        }
        return true;
    }

    @Override
    public List<Task> getHistory() {
        for (Task task : history) {
            System.out.println(task.getName());
        }
        return history;
    }
}
