package ru.yandex.kanban.service;
public class Managers {
    private static ru.yandex.kanban.service.TaskManager TaskManager;

    public static HistoryManager getDefaultTaskManager() {
        InMemoryHistoryManager InMemoryHistoryManager = new InMemoryHistoryManager();
        return InMemoryHistoryManager;
    }

    public static TaskManager getDefault() { /// maybe it's error
        return TaskManager;
    }
}
