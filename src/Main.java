import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;
import ru.yandex.kanban.service.InMemoryTaskManager;
import ru.yandex.kanban.model.Status;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        int task1 = manager.createNewTask(new Task("First Task", "create first task"));
        int task2 = manager.createNewTask(new Task("Second Task", "create second task"));
        int epic1 = manager.createNewEpic(new Epic("First Epic", "create first epic"));
        int subTask1 = manager.createNewSubTask(new Subtask("First Subtask", "it's first subtask for first epic", 2));
        int subTask2 = manager.createNewSubTask(new Subtask("Second Subtask", "it's second subtask for first epic", 2));
        int epic2 = manager.createNewEpic(new Epic("Second Epic", "Create second epic"));
        int subtask3 = manager.createNewSubTask(new Subtask("Third Subtask", "it's third subtask for second epic", 5));
        manager.updateTask(manager.getTaskById(task1), Status.IN_PROGRESS);
        manager.updateTask(manager.getTaskById(task1), Status.DONE);
        manager.updateTask(manager.getTaskById(task2), Status.IN_PROGRESS);
        manager.updateSubtask(manager.getSubtaskById(subTask1), Status.DONE);
        manager.updateSubtask(manager.getSubtaskById(subTask2), Status.DONE);
        int subtask4 = manager.createNewSubTask(new Subtask("Fourth Subtask", "4", 2));
        manager.updateSubtask(manager.getSubtaskById(subtask4), Status.DONE);
        manager.deleteSubtaskById(manager.getSubtaskById(subtask4).getId());
        //manager.deleteEpicById(epic2);
        for (Task task : manager.getAllTasks()) {
            System.out.format("name : %s | ID : %s | status : %s | \n", task.getName(), task.getId(), task.getStatus());
        }
        System.out.println(manager.getHistory());
    }
}
