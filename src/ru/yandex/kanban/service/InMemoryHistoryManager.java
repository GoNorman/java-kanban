package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Task;
import ru.yandex.kanban.model.Node;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Integer, Node> nodeMap = new HashMap<>();

    Node<Task> last;
    Node<Task> first;
    int size = 0;

    public Node<Task> linkLast(Task task) {
        final Node<Task> l = last;
        final Node<Task> newNode = new Node<>(l, task, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        nodeMap.put(task.getId(), newNode);
        size++;
        return newNode;
    }

    Task unlink(Node<Task> nodeTask) {
        final Task element = nodeTask.data;
        final Node<Task> prev = nodeTask.prev;
        final Node<Task> next = nodeTask.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            nodeTask.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            nodeTask.next = null;
        }

        nodeTask.data = null;
        size--;

        return element;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> result = new ArrayList<>();
        Node node = first;

        while (node != null) {
            result.add((Task) node.data);
            node = node.next;
        }

        return result;
    }

    @Override
    public boolean add(Task task) {
        if (task == null) {
            return false;
        }

        if (nodeMap.get(task.getId()) != null) {
            remove(task.getId());
            nodeMap.put(task.getId(), linkLast(task));
        } else {
            nodeMap.put(task.getId(), linkLast(task));
        }
        return true;
    }

    @Override
    public void remove(int id) { // take Node of task and delete task from List with node
        unlink(nodeMap.get(id));
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}
