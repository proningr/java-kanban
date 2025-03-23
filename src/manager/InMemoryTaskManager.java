package manager;

import enums.Status;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private final Map<Integer, Task> registeredTasks = new HashMap<>();
    private final Map<Integer, Epic> registeredEpics = new HashMap<>();
    private final Map<Integer, Subtask> registeredSubtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    private int currentID = 0;

    @Override
    public int getNextID() {
        return currentID++;
    }

    @Override
    public void addTask(Task task) {
        task.setId(getNextID());
        registeredTasks.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(getNextID());
        registeredEpics.put(epic.getId(), epic);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(getNextID());
        Epic epic = registeredEpics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
        registeredSubtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epic);
    }

    @Override
    public Task updateTask(Task task) {
        if (task == null || !registeredTasks.containsKey(task.getId())) {
            return task;
        }
        registeredTasks.replace(task.getId(), task);
        return task;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        if (epic == null || !registeredEpics.containsKey(epic.getId())) {
            return epic;
        }
        registeredEpics.replace(epic.getId(), epic);
        updateEpicStatus(epic);
        return epic;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        if (subtask == null || !registeredSubtasks.containsKey(subtask.getId())) {
            return subtask;
        }
        Subtask oldSubtask = registeredSubtasks.get(subtask.getId());
        registeredSubtasks.replace(subtask.getId(), subtask);
        Epic epic = registeredEpics.get(subtask.getEpicID());
        ArrayList<Subtask> subtaskList = epic.getSubtaskList();
        subtaskList.remove(oldSubtask);
        subtaskList.add(subtask);
        updateEpicStatus(epic);
        return subtask;
    }

    @Override
    public Task getTaskByID(int id) {
        Task task = registeredTasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicByID(int id) {
        Epic epic = registeredEpics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskByID(int id) {
        Subtask subtask = registeredSubtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(registeredTasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(registeredEpics.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(registeredSubtasks.values());
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        return epic.getSubtaskList();
    }

    @Override
    public void deleteTasks() {
        registeredTasks.clear();
    }

    @Override
    public void deleteEpics() {
        registeredEpics.clear();
        registeredSubtasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        registeredSubtasks.clear();
        for (Epic epic : registeredEpics.values()) {
            epic.clearSubtasks();
            epic.setStatus(Status.NEW);
        }
    }

    @Override
    public void deleteTaskByID(int id) {
        registeredTasks.remove(id);
    }

    @Override
    public void deleteEpicByID(int id) {
        ArrayList<Subtask> epicSubtasks = registeredEpics.get(id).getSubtaskList();
        registeredEpics.remove(id);
        for (Subtask subtask : epicSubtasks) {
            registeredSubtasks.remove(subtask.getId());
        }
    }

    @Override
    public void deleteSubtaskByID(int id) {
        Subtask subtask = registeredSubtasks.remove(id);
        Epic epic = registeredEpics.get(subtask.getEpicID());
        ArrayList<Subtask> subtaskList = epic.getSubtaskList();
        subtaskList.remove(subtask);
        updateEpicStatus(epic);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateEpicStatus(Epic epic) {
        int doneCount = 0;
        int newCount = 0;
        ArrayList<Subtask> list = new ArrayList<>(epic.getSubtaskList());

        for (Subtask subtask : list) {
            if (subtask.getStatus() == Status.DONE) {
                doneCount++;
            }
            if (subtask.getStatus() == Status.NEW) {
                newCount++;
            }
        }
        if (doneCount == list.size()) {
            epic.setStatus(Status.DONE);
        } else if (newCount == list.size()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }

    }

}