package ru.yandex.kanban.model;

public class ManagerSaveException extends RuntimeException {

    private String message;
    public ManagerSaveException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
