package ru.yandex.kanban.service;
public class Managers {
    public static InMemoryHistoryManager getDefaultTaskManager() {
        InMemoryHistoryManager InMemoryHistoryManager = new InMemoryHistoryManager();
        return InMemoryHistoryManager;
    }
}
