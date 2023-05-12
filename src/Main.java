import org.w3c.dom.Node;
import ru.yandex.kanban.service.*;
import ru.yandex.kanban.model.*;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        FileBackedTasksManager manager1 = new FileBackedTasksManager();
        int task1 = manager1.createNewTask(new Task("First Task", "create first task"));
        int task2 = manager1.createNewTask(new Task("Second Task", "create second task"));
        int epic1 = manager1.createNewEpic(new Epic("First Epic", "create first epic"));
        int subTask1 = manager1.createNewSubTask(new Subtask("First Subtask", "it's first subtask for first epic", 2));
        int subTask2 = manager1.createNewSubTask(new Subtask("Second Subtask", "it's second subtask for first epic", 2));
        int epic2 = manager1.createNewEpic(new Epic("Second Epic", "Create second epic"));
        int subtask3 = manager1.createNewSubTask(new Subtask("Third Subtask", "it's third subtask for second epic", 5));
        manager1.updateTask(manager1.getTaskById(task1), Status.IN_PROGRESS);
        manager1.updateTask(manager1.getTaskById(task1), Status.DONE);
        manager1.updateTask(manager1.getTaskById(task2), Status.IN_PROGRESS);
        manager1.updateSubtask(manager1.getSubtaskById(subTask1), Status.DONE);
        manager1.updateSubtask(manager1.getSubtaskById(subTask2), Status.DONE);
        int subtask4 = manager1.createNewSubTask(new Subtask("Fourth Subtask", "4", 2));
        //manager1.updateSubtask(manager1.getSubtaskById(subtask4), Status.DONE);
        //manager1.deleteSubtaskById(manager.getSubtaskById(subtask4).getId());
        //System.out.println(manager1.getHistory());
        manager1.readFileTask();
    }
}
