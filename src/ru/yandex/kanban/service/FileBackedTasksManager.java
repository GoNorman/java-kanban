package ru.yandex.kanban.service;
import ru.yandex.kanban.model.*;

import java.io.*;
import java.util.List;

import static ru.yandex.kanban.model.Status.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    static protected String fileName = "resources/data.csv";

    public boolean save() {
        try (Writer file = new FileWriter(fileName)) {
            file.append("id,type,name,description,status,epic\n");
            for (Task task : allTasksHashMap.values()) {
                file.append(task.toString() + "\n");
            }
           file.append("\n");
            if (!getHistory().isEmpty()) {
                List<Task> taskList = (List<Task>) super.getHistory();
                for (Task task : taskList) {
                    file.append(task.getId() + ",");
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
        return true;
    }


    public static FileBackedTasksManager loadFromFile() {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        try(Reader files = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(files);
            String line = "";
            boolean historyWatch = false;
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    historyWatch = true;
                    continue;
                }
                String[] data = line.split(",");
                if (!historyWatch) {
                        int id = Integer.parseInt(data[0]);
                        String typeOfTask = data[1];
                        String name = data[2];
                        String description = data[3];
                        String status = data[4];
                    if (Tasks.TASK == Tasks.valueOf(typeOfTask)) {
                        Task task = new Task(name, description);
                        task.setId(id);
                        fileBackedTasksManager.createNewTask(task); // деффолтно ставиться NEW
                        task.setStatus(Status.valueOf(status));//тут берем статус из файла
                    } else if (Tasks.EPIC == Tasks.valueOf(typeOfTask)) {
                        Epic epic = new Epic(name, description);
                        epic.setId(id);
                        fileBackedTasksManager.createNewEpic(epic);
                        epic.setStatus(Status.valueOf(status));
                    } else if (Tasks.SUBTASK == Tasks.valueOf(typeOfTask)) {
                        int epicId = Integer.parseInt(data[5]); // чтобы не выйти за пределы во время чтения
                        Subtask subtask = new Subtask(name, description, epicId);
                        subtask.setId(id);
                        fileBackedTasksManager.createNewSubTask(subtask);// в самом методе идет линковка к эпику
                        subtask.setStatus(Status.valueOf(status));
                    }
                } else {
                    for (int i = 0; i < data.length; i++) {
                        Task task = fileBackedTasksManager.allTasksHashMap.get(i);
                        fileBackedTasksManager.historyManager.add(task);
                    }
                }
            }
        } catch (IOException exception) {
            throw new ManagerSaveException(exception);
        }
        return fileBackedTasksManager;
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
        Task task = super.getTaskById(taskId);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = super.getEpicById(epicId);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = super.getSubtaskById(subtaskId);
        save();
        return subtask;
    }

    @Override
    public boolean updateTask(Task task, Status status) {
        boolean res = super.updateTask(task, status);
        save();
        return res;
    }

    @Override
    public boolean updateEpic(Epic epic) {
        boolean res = super.updateEpic(epic);
        save();
        return res;
    }

    @Override
    public boolean updateSubtask(Subtask subtask, Status status) {
        boolean res = super.updateSubtask(subtask, status);
        save();
        return res;
    }

    @Override
    public boolean deleteTaskById(int id) {
        boolean res = super.deleteTaskById(id);
        save();
        return res;
    }

    @Override
    public boolean deleteEpicById(int id) {
        boolean res = super.deleteEpicById(id);
        save();
        return res;
    }

    @Override
    public boolean deleteSubtaskById(int id) {
        boolean res = super.deleteSubtaskById(id);
        save();
        return res;
    }

}
