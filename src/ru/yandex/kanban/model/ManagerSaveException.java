package ru.yandex.kanban.model;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException(Exception message) {
        super(message);
    }
}
