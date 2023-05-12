package ru.yandex.kanban.model;

import java.io.IOException;

public class ManagerSaveException extends IOException {

    private String message;
    public ManagerSaveException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
