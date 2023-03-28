# java-kanban
Repository for homework project.

Update project and check git



package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Manager {
private int id = 0;
private HashMap<Integer, Task> taskHashMap = new HashMap<>();
private HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
private HashMap<Integer, Epic> epicHashMap = new HashMap<>();
private HashMap<Integer, Task> allTasksHashMap = new HashMap<>();

    public int createNewTask(Task task) {
        task.setId(id++);
        task.setStatus("NEW");
        allTasksHashMap.put(task.getId(), task);
        taskHashMap.put(task.getId(), task);
        return task.getId();
    }

    public int createNewEpic(Epic epic) {
        epic.setId(id++);
        epic.setStatus("NEW");
        epicHashMap.put(epic.getId(), epic);
        allTasksHashMap.put(epic.getId(), epic);
        return epic.getId();
    }

    public int createNewSubTask(Subtask subtask) {
        subtask.setId(id++);
        subtask.setStatus("NEW");
        subtaskHashMap.put(subtask.getId(), subtask);
        allTasksHashMap.put(subtask.getId(), subtask);
        Epic epic = getEpicById(subtask.getEpicId());
        List<Subtask> subtaskList = epic.getSubtaskList();
        subtaskList.add(subtask);
        updateStatusEpic(subtask.getEpicId());
        return subtask.getId();
    }

    public Task getTaskById(int taskId) {
        return taskHashMap.get(taskId);
    }

    public Epic getEpicById(int epicId) {
        return epicHashMap.get(epicId);
    }

    public Subtask getSubtaskById(int subtaskId) {
        return subtaskHashMap.get(subtaskId);
    }

    public List<Task> getAllTasks() {
        List<Task> list = new ArrayList<>(allTasksHashMap.values());
        return list;
    }

    public List<Task> getAllTask() {
        List<Task> taskList= new ArrayList<>(taskHashMap.values());
        return taskList;
    }

    public List<Epic> getAllEpic() {
        List<Epic> epicList = new ArrayList<>(epicHashMap.values());
        return epicList;
    }

    public List<Subtask> getAllSubtask() {
        List<Subtask> subtaskList = new ArrayList<>(subtaskHashMap.values());
        return subtaskList;
    }

    public boolean deleteAllTasks() {
        if (allTasksHashMap.isEmpty()) {
            return false;
        }
        allTasksHashMap.clear();
        taskHashMap.clear();
        epicHashMap.clear();
        subtaskHashMap.clear();
        return true;
    }

    public boolean deleteTaskById(int id) {
        if (taskHashMap.get(id) != null) {
            taskHashMap.remove(id);
            allTasksHashMap.remove(id);
            return true;
        }
        return false;
    }

    public boolean deleteEpicById(int id) {
        if (epicHashMap.get(id) != null) {
            Epic epic = getEpicById(id);
            List<Subtask> subtasksList = epic.getSubtaskList();
            for (Subtask subtask : subtasksList) {
                subtaskHashMap.remove(subtask.getId());
                allTasksHashMap.remove(subtask.getId());
            }
            epicHashMap.remove(id);
            allTasksHashMap.remove(epic.getId());
            return true;
        }
        return false;
    }

    public boolean deleteSubtaskById(int id) {
        if (subtaskHashMap.get(id) != null) {
            Epic epic = epicHashMap.get(subtaskHashMap.get(id).getEpicId());
            Subtask subtask = getSubtaskById(id);
            epic.getSubtaskList().remove(subtask);
            allTasksHashMap.remove(subtask.getId());
            updateStatusEpic(subtask.getEpicId());
            subtaskHashMap.remove(subtask.getId());
            return true;
        }
        return false;
    }

    public boolean updateTask(Task task, String status) {
        if (taskHashMap.get(task.getId()) != null) {
            Task taskNew = task;
            taskNew.setStatus(status);
            taskHashMap.replace(task.getId(), taskNew);
            allTasksHashMap.replace(task.getId(), taskNew);
            return true;
        }
        return false;
    }

    public boolean updateEpic(Epic epic) {
        if (epicHashMap.get(epic.getId()) != null) {//We don't need change status for Epic. Because status will change -
            epicHashMap.replace(epic.getId(), epic);//with subtask together.
        }
        return false;
    }

    public boolean updateSubtask(Subtask subtask, String status) {
        if (subtaskHashMap.get(subtask.getId()) != null) {
            Subtask subtaskNew = subtask;
            subtaskNew.setStatus(status);
            subtaskHashMap.replace(subtask.getId(), subtaskNew);
            updateStatusEpic(subtaskNew.getEpicId()); //Check method for Epic. Check subtask of epic.
        }
        return false;
    }

    public List<Subtask> getSubtaskListFromEpic(int id) {
        if (epicHashMap.get(id) == null) {
            return Collections.emptyList();
        }
        Epic epic = epicHashMap.get(id);
        return epic.getSubtaskList();
    }

    private boolean updateStatusEpic(int epicId) {
        if (epicHashMap.get(epicId) == null) {
            return false;
        }
        Epic epic = epicHashMap.get(epicId);
        List<Subtask> subtaskList = epic.getSubtaskList();
        int taskInProgress = 0;
        int taskDoneCount = 0;
        for (Subtask sub : subtaskList) {
            if (sub.getStatus().equals("IN_PROGRESS")) {
                taskInProgress++;
            }
            if (sub.getStatus().equals("DONE")) {
                taskDoneCount++;
            }
        }
        if (taskInProgress > 0) {
            epic.setStatus("IN_PROGRESS");
        } else {
            epic.setStatus("NEW");
        }
        if (taskDoneCount == subtaskList.size()) {
            epic.setStatus("DONE");
        } else {
            epic.setStatus("NEW");
        }
        return true;
    }
}

