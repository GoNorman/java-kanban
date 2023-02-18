import ru.yandex.kanban.module.Epic;
import ru.yandex.kanban.module.Subtask;
import ru.yandex.kanban.module.Task;
import ru.yandex.kanban.service.Manager;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.createNewTask(new Subtask(), "create a first subtask", "first task");
        manager.createNewTask(new Subtask(), "create a second subtask", "second task");
        manager.createNewTask(new Epic(), "create a first epic", "epic task");
        manager.createNewTask(new Task(), "it's a one task", "task");
        manager.checkMethod();
        Task idSub = manager.getTaskById(0);
        System.out.println(idSub.getName());
    }
}
