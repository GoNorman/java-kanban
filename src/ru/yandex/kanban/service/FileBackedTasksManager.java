package ru.yandex.kanban.service;
import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;

import java.io.*;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    public static void main(String[] args) throws IOException {

    }

    public boolean save() {
        try (Writer file = new FileWriter("src/ru/yandex/kanban/file/data.csv")) {
            file.append("id,type,name,description,status,epic\n"); /// Create the field
            for (Task task : allTasksHashMap.values()) {
                file.append(task.toString()+"\n");
            }
        } catch (IOException ex) {
            ex.getMessage();
            return false;
        }

        return true;
    }

    public boolean readFileTask() throws IOException {
        try(Reader file = new FileReader("src/ru/yandex/kanban/file/data.csv")) {
            BufferedReader br = new BufferedReader(file);
            while(br.ready()) { /// как пропустить первую строку id,type,name,description,status,epic?
                System.out.println(br.readLine());
            }
        } catch (IOException exception) {
            exception.getMessage();
        }
        return true;
    }

    @Override
    public int createNewTask(Task task) {
        super.createNewTask(task);
        save();
        return task.getId();
    }

    @Override
    public int createNewEpic(Epic epic) {
        super.createNewEpic(epic);
        save();
        return epic.getId();
    }

    @Override
    public int createNewSubTask(Subtask subtask) {
        super.createNewSubTask(subtask);
        save();
        return subtask.getId();
    }
}
