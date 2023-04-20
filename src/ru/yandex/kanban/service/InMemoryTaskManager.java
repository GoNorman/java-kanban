package ru.yandex.kanban.service;

import ru.yandex.kanban.model.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    Map<Integer, Task> taskHashMap = new HashMap<>();
    Map<Integer, Subtask> subtaskHashMap = new HashMap<>();
    Map<Integer, Epic> epicHashMap = new HashMap<>();
    Map<Integer, Task> allTasksHashMap = new HashMap<>();
    HistoryManager historyManager = Managers.getDefaultHistoryManager();

    @Override
    public int createNewTask(Task task) {
        task.setId(id++);
        task.setStatus(Status.NEW);
        allTasksHashMap.put(task.getId(), task);
        taskHashMap.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public int createNewEpic(Epic epic) {
        epic.setId(id++);
        epic.setStatus(Status.NEW);
        epicHashMap.put(epic.getId(), epic);
        allTasksHashMap.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public int createNewSubTask(Subtask subtask) {
        subtask.setId(id++);
        subtask.setStatus(Status.NEW);
        subtaskHashMap.put(subtask.getId(), subtask);
        allTasksHashMap.put(subtask.getId(), subtask);
        Epic epic = getEpicById(subtask.getEpicId());
        List<Subtask> subtaskList = epic.getSubtaskList();
        subtaskList.add(subtask);
        updateStatusEpic(subtask.getEpicId());
        return subtask.getId();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = taskHashMap.get(taskId);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epicHashMap.get(epicId);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = subtaskHashMap.get(subtaskId);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> list = new ArrayList<>(allTasksHashMap.values());
        return list;
    }

    @Override
    public ArrayList<Task> getAllTask() {
        ArrayList<Task> taskList = new ArrayList<>(taskHashMap.values());
        return taskList;
    }

    @Override
    public List<Epic> getAllEpic() {
        List<Epic> epicList = new ArrayList<>(epicHashMap.values());
        return epicList;
    }

    @Override
    public List<Subtask> getAllSubtask() {
        List<Subtask> subtaskList = new ArrayList<>(subtaskHashMap.values());
        return subtaskList;
    }

    @Override
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

    @Override
    public boolean deleteTaskById(int id) {
        if (taskHashMap.get(id) != null) {
            taskHashMap.remove(id);
            allTasksHashMap.remove(id);
            return true;
        }
        return false;
    }

    @Override
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

    @Override
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

    @Override
    public boolean updateTask(Task task, Status status) { /// 3
        if (taskHashMap.get(task.getId()) != null) {
            Task taskNew = task;
            taskNew.setStatus(status);
            taskHashMap.replace(task.getId(), taskNew);
            allTasksHashMap.replace(task.getId(), taskNew);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateEpic(Epic epic) {
        if (epicHashMap.get(epic.getId()) != null) {//We don't need change status for Epic. Because status will change -
            epicHashMap.replace(epic.getId(), epic);//with subtask together.
        }
        return false;
    }

    @Override
    public boolean updateSubtask(Subtask subtask, Status status) {
        if (subtaskHashMap.get(subtask.getId()) != null) {
            Subtask subtaskNew = subtask;
            subtaskNew.setStatus(status);
            subtaskHashMap.replace(subtask.getId(), subtaskNew);
            updateStatusEpic(subtaskNew.getEpicId()); //Check method for Epic. Check subtask of epic.
        }
        return false;
    }

    @Override
    public List<Subtask> getSubtaskListFromEpic(int id) {
        if (epicHashMap.get(id) == null) {
            return Collections.emptyList();
        }
        Epic epic = epicHashMap.get(id);
        return epic.getSubtaskList();
    }

    @Override
    public boolean updateStatusEpic(int epicId) {
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
            epic.setStatus(Status.IN_PROGRESS);
        } else {
            epic.setStatus(Status.NEW);
        }
        if (taskDoneCount == subtaskList.size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.NEW);
        }
        return true;
    }
    @Override
    public List getHistory() {
        return historyManager.getHistory();
    }
}
