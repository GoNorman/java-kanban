package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Task;
import ru.yandex.kanban.model.Node;

import java.util.*;
public class InMemoryHistoryManager implements HistoryManager {

    private ArrayList<Task> history = new ArrayList<>(); /// Need be custom
    private HashMap<Integer, Node> linkTasks = new HashMap<>();

    transient Node<Task> last;
    transient Node<Task> first;
    transient int size = 0;

    public void linkLast(Task task) {
        final Node<Task> l = last;
        final Node<Task> newNode = new Node<>(l, task, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        linkTasks.put(task.getId(), newNode);
        size++;
    }

    public ArrayList<Task> getTasks() {
        return history;
    }

    public boolean removeNode(int ID) {
        if (linkTasks.get(ID) != null) {
            final Node<Task> node = linkTasks.get(ID);
            history.remove(node);
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Task task) { /// change with Node
        if (task == null) {
            return false;
        }
        if (getHistory().size() >= 10) {
            getHistory().remove(0);
            history.add(task);
            linkLast(task);
        } else {
            linkLast(task);
            history.add(task);
        }
        return true;
    }

    @Override
    public void remove(int id) { // take Node of task and delete task from List with node
        removeNode(id);
    }

    @Override
    public List<Task> getHistory() {// Take data from LinkedList and Push to ArrayList
        return history;
    }
}
