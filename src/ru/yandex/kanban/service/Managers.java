package ru.yandex.kanban.service;
public class Managers {

    public static HistoryManager getDefaultHistoryManager() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
