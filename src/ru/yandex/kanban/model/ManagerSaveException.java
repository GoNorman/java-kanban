package ru.yandex.kanban.model;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {

    private String message;
    public ManagerSaveException(IOException message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
