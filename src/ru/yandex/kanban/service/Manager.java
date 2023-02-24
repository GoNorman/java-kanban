package ru.yandex.kanban.service;
import ru.yandex.kanban.module.Epic;
import ru.yandex.kanban.module.Subtask;
import ru.yandex.kanban.module.Task;
import java.util.HashMap;

public class Manager {
    private int id = 0;
    private HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
    private HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private HashMap<Integer, Task> allTaskHashMap = new HashMap<>();
    public int createNewTask(Task task, String name, String description) {
        task.setName(name);
        task.setDescription(description);
        task.setId(id++);
        task.setStatus("NEW");
        allTaskHashMap.put(task.getId(), task);
        if (task.getClass() == Task.class) taskHashMap.put(task.getId(), task);
        else if (task.getClass() == Epic.class) epicHashMap.put(task.getId(), (Epic) task);
        else if(task.getClass() == Subtask.class) {
            subtaskHashMap.put(task.getId(), (Subtask) task);

        }
        return task.getId();
    }

    public Task getTaskById(int id){
        for (Object obj : allTaskHashMap.values()) {
            Task task = (Task) obj;
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public HashMap getAllTasks() {
        return allTaskHashMap;
    }

    public HashMap deleteAllTasks() {
        allTaskHashMap.clear();
        return allTaskHashMap;
    }

    public Boolean deleteById(int id) {
        for (Task task : allTaskHashMap.values()) {
            if (task.getId() == id) {
                allTaskHashMap.remove(id);
                taskHashMap.remove(id);
                epicHashMap.remove(id);
                subtaskHashMap.remove(id);
                return true;
            }
        }
        return false;
    }

    public Boolean updateTask(Task task) {
        for (Task task1 : allTaskHashMap.values()) {
            if (task.getId() == task1.getId()) {
                if (task.getStatus().equals("NEW")) {
                    task.setStatus("IN_PROGRESS");
                } else if (task.getStatus().equals("IN_PROGRESS")) {
                    task.setStatus("DONE");
                }
                if (task.getClass() == Task.class) {
                    taskHashMap.replace(task.getId(), task);
                } else if (task.getClass() == Epic.class) {
                    Epic epic = (Epic) task;
                    epicHashMap.replace(epic.getId(), epic);
                } else if(task.getClass() == Subtask.class) {
                    Subtask subtask = (Subtask) task;
                    subtaskHashMap.replace(subtask.getId(), subtask);
                    UpdateStatusEpic(subtask); //Check method for Epic. Check subtask of epic.
                }
                allTaskHashMap.replace(task.getId(), task);
                return true;
            }
        }
        return false;
    }

    public Boolean linkSubtaskToEpic(int epicId, int subtaskId) {
        if (epicHashMap.get(epicId) != null && subtaskHashMap.get(subtaskId) != null) {
            Subtask subtask = subtaskHashMap.get(subtaskId);
            Epic epic = epicHashMap.get(epicId);
            subtask.setEpicId(epicId);
            epic.setValueToHashMap(subtaskId, subtaskHashMap.get(subtaskId));
            return true;
        }
        return false;
    }

    public HashMap<Integer, Subtask> getSubtaskList(int id) {
        if (epicHashMap.get(id) == null) {
            return null;
        }
        Epic epic = epicHashMap.get(id);
        return epic.getSubtaskHashMap();
    }
    public void showTask() { /// delete this method. It's method for check HashMap
        for (Task task : allTaskHashMap.values()) {
            System.out.format("name = %s | id = %s | status = %s |\n", task.getName(), task.getId(), task.getStatus());
        }
        System.out.println("================");
    }

    void UpdateStatusEpic(Subtask subtask) {
        if (epicHashMap.get(subtask.getEpicId()) == null) {
            return;
        }
        Epic epic = epicHashMap.get(subtask.getEpicId());
        HashMap<Integer, Subtask> integerSubtaskHashMap = epic.getSubtaskHashMap();
        int taskInProgress = 0;
        int taskDoneCount = 0;
        for (Subtask sub : integerSubtaskHashMap.values()) {
            if (sub.getStatus().equals("IN_PROGRESS")) {
                taskInProgress++;
            }
            if (sub.getStatus().equals("DONE")) {
                taskDoneCount++;
            }
        }
        if (taskInProgress > 0) {
            epic.setStatus("IN_PROGRESS");
        }
        if (taskDoneCount == integerSubtaskHashMap.size()) {
            epic.setStatus("DONE");
        }
    }
}
