package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Task;

public class TrackerStatus {
    public static boolean changeStatus(Task task) {
        if (task.getStatus() == null) {
            return false;
        }
        if (task.getStatus().equals("NEW")) {
            task.setStatus("IN_PROGRESS");
        } else if (task.getStatus().equals("IN_PROGRESS")) {
            task.setStatus("DONE");
        }
        return true;
    }
}
