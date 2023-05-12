package ru.yandex.kanban.service;
import ru.yandex.kanban.model.*;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    static protected String fileName = "src/resources/data.csv";

    public boolean save() {
        try (Writer file = new FileWriter(fileName)) {
            file.append("id,type,name,description,status,epic\n"); /// Create the field
            for (Task task : allTasksHashMap.values()) {
                file.append(task.toString()+"\n");
            }
            file.append("\n");
            if (!getHistory().isEmpty()) {
                List<Task> taskList = (List<Task>) super.getHistory();
                for (Task task : taskList) {
                    file.append(task.getId()+";");
                }
             }
        } catch (ManagerSaveException mse) {
            System.out.println(mse.getMessage());
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean readFileTask() { //// Create a new object "FileBackedTasksManager"
        try(Reader file = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(file);
            while (br.ready()) {
                System.out.println(br.readLine());
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
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

    @Override
    public Task getTaskById(int taskId) {
        save();
        return super.getTaskById(taskId);
    }

    @Override
    public Epic getEpicById(int epicId) {
        save();
        return super.getEpicById(epicId);
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        save();
        return super.getSubtaskById(subtaskId);
    }

    @Override
    public boolean updateTask(Task task, Status status) {
        save();
        return super.updateTask(task, status);
    }

    @Override
    public boolean updateEpic(Epic epic) {
        save();
        return super.updateEpic(epic);
    }

    @Override
    public boolean updateSubtask(Subtask subtask, Status status) {
        save();
        return super.updateSubtask(subtask, status);
    }

    @Override
    public boolean deleteTaskById(int id) {
        save();
        return super.deleteTaskById(id);
    }

    @Override
    public boolean deleteEpicById(int id) {
        save();
        return super.deleteEpicById(id);
    }

    @Override
    public boolean deleteSubtaskById(int id) {
        save();
        return super.deleteSubtaskById(id);
    }

}
