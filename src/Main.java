import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;
import ru.yandex.kanban.service.Manager;

import java.util.Formattable;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        int task1 = manager.createNewTask(new Task("First Task", "create first task"));
        int task2 = manager.createNewTask(new Task("Second Task", "create second task"));
        int epic1 = manager.createNewEpic(new Epic("First Epic", "create first epic"));
        int subTask1 = manager.createNewSubTask(new Subtask("First Subtask", "it's first subtask for first epic", 2));
        int subTask2 = manager.createNewSubTask(new Subtask("Second Subtask", "it's second subtask for first epic", 2));
        int epic2 = manager.createNewEpic(new Epic("Second Epic", "Create second epic"));
        int subtask3 = manager.createNewSubTask(new Subtask("Third Subtask", "it's third subtask for second epic", 5));
        manager.updateStatusTask(manager.getTaskById(task1));
        manager.updateStatusTask(manager.getTaskById(task1));
        manager.updateStatusTask(manager.getTaskById(task2));
        manager.updateStatusSubtask(manager.getSubtaskId(subTask1));
        manager.updateStatusSubtask(manager.getSubtaskId(subTask1));
        manager.updateStatusSubtask(manager.getSubtaskId(subTask2));
        manager.updateStatusSubtask(manager.getSubtaskId(subTask2));
        int subtask4 = manager.createNewSubTask(new Subtask("Fourth Subtask", "4", 2));
        manager.updateStatusSubtask(manager.getSubtaskId(subtask4));
        manager.updateStatusSubtask(manager.getSubtaskId(subtask4));
        manager.deleteSubtaskById(manager.getSubtaskId(subtask4).getId());
        //manager.deleteEpicById(epic2);
        for (Task task : manager.getAllTasks()) {
            System.out.format("name : %s | ID : %s | status : %s | \n", task.getName(), task.getId(), task.getStatus());
        }
    }
}
