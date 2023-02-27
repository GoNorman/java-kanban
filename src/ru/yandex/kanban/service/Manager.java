package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Manager {
    private int id = 0;
    private HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
    private HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private HashMap<Integer, Task> allTasksHashMap = new HashMap<>();
    private ArrayList<Task> allTaskList = new ArrayList<>();

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
        if (getEpicById(epic.getId()).getStatus().equals("DONE")) {
            epic.setStatus("NEW");
        }
        return subtask.getId();
    }

    public Task getTaskById(int taskId) {
        return taskHashMap.get(taskId);
    }

    public Epic getEpicById(int epicId) {
        return epicHashMap.get(epicId);
    }

    public Subtask getSubtaskId(int subtaskId) {
        return subtaskHashMap.get(subtaskId);
    }

    public List<Task> getAllTasks() {
        List<Task> list = new ArrayList<>(allTasksHashMap.values());
        return list;
    }

    public List<Task> deleteAllTasks() {
        allTasksHashMap.clear();
        taskHashMap.clear();
        epicHashMap.clear();
        subtaskHashMap.clear();
        return allTaskList;
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
            epic.getSubtaskList().remove(getSubtaskId(id));
            allTasksHashMap.remove(getSubtaskId(id).getId());
            return true;
        }
        return false;
    }

    public boolean updateTask(Task task) {
        if (taskHashMap.get(task.getId()) != null) {
            taskHashMap.replace(task.getId(), task);
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

    public boolean updateSubtask(Subtask subtask) {
        if (subtaskHashMap.get(subtask.getId()) != null) {
            subtaskHashMap.replace(subtask.getId(), subtask);
            updateStatusEpic(subtask); //Check method for Epic. Check subtask of epic.
        }
        return false;
    }

    public List<Subtask> getSubtaskListFromEpic(int id) {
        if (epicHashMap.get(id) == null) {
            return null;
        }
        Epic epic = epicHashMap.get(id);
        return epic.getSubtaskList();
    }


    public boolean updateStatusTask(Task task) {
        if (taskHashMap.get(task.getId()) == null) {
            return false;
        }
        TrackerStatus.changeStatus(task); /// Maybe it's not right and don't use static method from a different class
        return true;
    }

    public boolean updateStatusSubtask(Subtask subtask) {
        if (subtaskHashMap.get(subtask.getId()) == null) {
            return false;
        }
        TrackerStatus.changeStatus(subtask);
        updateSubtask(subtask);
        return true;
    }

    private boolean updateStatusEpic(Subtask subtask) {
        if (epicHashMap.get(subtask.getEpicId()) == null) {
            return false;
        }
        Epic epic = epicHashMap.get(subtask.getEpicId());
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
