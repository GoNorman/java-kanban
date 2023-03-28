package ru.yandex.kanban.model;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    public int createNewTask(Task task);
    public int createNewEpic(Epic epic);
    public int createNewSubTask(Subtask subtask);
    public Task getTaskById(int taskId);
    public Epic getEpicById(int epicId);
    public Subtask getSubtaskById(int subtaskId);
    public List<Task> getAllTasks();
    public List<Task> getAllTask();
    public List<Epic> getAllEpic();
    public List<Subtask> getAllSubtask();
    public boolean deleteAllTasks();
    public boolean deleteTaskById(int id);
    public boolean deleteEpicById(int id);
    public boolean deleteSubtaskById(int id);
    public boolean updateTask(Task task, Status status);
    public boolean updateEpic(Epic epic);
    public boolean updateSubtask(Subtask subtask, Status status);
    public List<Subtask> getSubtaskListFromEpic(int id);
    public boolean updateStatusEpic(int epicId);
    public Task getTask(Task task);
    public Epic getEpic(Epic epic);
    public Subtask getSubtask(Subtask subtask);
}
