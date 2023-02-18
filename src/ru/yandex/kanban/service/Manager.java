package ru.yandex.kanban.service;
import ru.yandex.kanban.module.Epic;
import ru.yandex.kanban.module.Subtask;
import ru.yandex.kanban.module.Task;
import java.util.HashMap;

public class Manager {
    private int id = 0;
    HashMap<Integer, Task> taskHashMap = new HashMap<>();
    HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
    HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    HashMap<Integer, Task> allTaskHashMap = new HashMap<>();
    public void createNewTask(Task task, String name, String description) {
        task.setName(name);
        task.setDescription(description);
        task.setId(id++);
        task.setStatus("NEW");
        allTaskHashMap.put(task.getId(), task);
        if (task.getClass() == Task.class) taskHashMap.put(task.getId(), task);
        else if (task.getClass() == Epic.class) epicHashMap.put(task.getId(), (Epic) task);
        else if(task.getClass() == Subtask.class) subtaskHashMap.put(task.getId(), (Subtask) task);

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
    public void checkMethod() {
        for (Task task : allTaskHashMap.values()) {
            System.out.println(task.getName());
        }
    }

    //public void DeleteAllTask(){}
    //public HashMap getAllTasks(){}
}
