package ru.yandex.kanban.service;
import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;

import java.io.*;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    Map<Integer, Task> allTask = new HashMap<>(super.allTasksHashMap);
    public static void main(String[] args) throws IOException {
        try(Reader file = new FileReader("src/ru/yandex/kanban/file/data.csv")) {
            BufferedReader br = new BufferedReader(file);
            if (br.ready()) {
                System.out.println(br.readLine());
            }
        } catch (IOException exception) {
            exception.getMessage();
        }
    }

    public boolean save() {
        try (Writer file = new FileWriter("src/ru/yandex/kanban/file/data.csv")) {
            file.append("id,type,name,status,description,epic\n"); /// Create the field
            // create loops for
        } catch (IOException ex) {
            ex.getMessage();
        }
        for (Task task : allTask.values()) {
            System.out.println(task.getName());
        }
        return true;
    }

    @Override
    public int createNewTask(Task task) {
        super.createNewTask(task);
        save();
        return task.getId();
    }

    @Override
    public int createNewEpic(Epic epic) {
        super.createNewEpic(epic);
        save();
        return epic.getId();
    }

    @Override
    public int createNewSubTask(Subtask subtask) {
        super.createNewSubTask(subtask);
        save();
        return subtask.getId();
    }
}
