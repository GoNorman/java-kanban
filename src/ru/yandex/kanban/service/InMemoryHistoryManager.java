package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Task;
import ru.yandex.kanban.model.Node;

import java.lang.reflect.Array;
import java.util.*;
public class InMemoryHistoryManager implements HistoryManager {


    private LinkedList<Task> history = new LinkedList<>(); /// Custom LinkedList
    private Map<Integer, Node> nodeMap = new HashMap<>(); // нужно стараться везде объявлять переменную типа интерфейса

    Node<Task> last;
    Node<Task> first;
    int size = 0;

    public void linkLast(Task task) {
        final Node<Task> l = last;
        final Node<Task> newNode = new Node<>(l, task, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        nodeMap.put(task.getId(), newNode);
        size++;
    }

    public ArrayList<Task> getTasks() { /// Convert LinkedList to ArrayList
        ArrayList<Task> result = new ArrayList<>(history);
        return result;
    }

    public boolean removeNode(int ID) {
        if (nodeMap.get(ID) != null) {
            final Node<Task> node = nodeMap.get(ID);
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
        if (history.get(task.getId()) != null) {/// delete from linkedList (duplicate)
            
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
