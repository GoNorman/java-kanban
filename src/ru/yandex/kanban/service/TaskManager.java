package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Status;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;
import java.util.List;

public interface TaskManager {
    int createNewTask(Task task);
    int createNewEpic(Epic epic);
    int createNewSubTask(Subtask subtask);
    Task getTaskById(int taskId);
    Epic getEpicById(int epicId);
    Subtask getSubtaskById(int subtaskId);
    List<Task> getAllTasks();
    List<Task> getAllTask();
    List<Epic> getAllEpic();
    List<Subtask> getAllSubtask();
    boolean deleteAllTasks();
    boolean deleteTaskById(int id);
    boolean deleteEpicById(int id);
    boolean deleteSubtaskById(int id);
    boolean updateTask(Task task, Status status);
    boolean updateEpic(Epic epic);
    boolean updateSubtask(Subtask subtask, Status status);
    List<Subtask> getSubtaskListFromEpic(int id);
    boolean updateStatusEpic(int epicId);
}
