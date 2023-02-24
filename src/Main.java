import ru.yandex.kanban.module.Epic;
import ru.yandex.kanban.module.Subtask;
import ru.yandex.kanban.module.Task;
import ru.yandex.kanban.service.Manager;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        int task1 = manager.createNewTask(new Task(), "First Task", "create first task");
        int task2 = manager.createNewTask(new Task(), "Second Task", "create second task");
        int epic1 = manager.createNewTask(new Epic(), "First Epic", "create first epic");
        int subTask1 = manager.createNewTask(new Subtask(), "First Subtask", "it's first subtask for first epic");
        int subTask2 = manager.createNewTask(new Subtask(), "Second Subtask", "it's second subtask for first epic");
        int epic2 = manager.createNewTask(new Epic(), "Second Epic", "Create second epic");
        int subtask3 = manager.createNewTask(new Subtask(), "Third Subtask", "it's third subtask for second epic");
        manager.updateTask(manager.getTaskById(task1));
        manager.showTask();
        manager.updateTask(manager.getTaskById(task1));
        manager.showTask();
        manager.updateTask(manager.getTaskById(task2));
        manager.showTask();
        manager.linkSubtaskToEpic(epic1, subTask1);
        manager.linkSubtaskToEpic(epic1, subTask2);
        manager.linkSubtaskToEpic(epic2, subtask3);
        manager.showTask();
        manager.updateTask(manager.getTaskById(subTask1));
        manager.showTask();
        manager.updateTask(manager.getTaskById(subTask1));
        manager.showTask();
        manager.updateTask(manager.getTaskById(subTask2));
        //manager.updateTask(manager.getTaskById(subTask2));
        HashMap<Integer, Subtask> hashMapSub = manager.getSubtaskList(epic1);
        manager.showTask();
    }
}
