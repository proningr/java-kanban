package manager;
import java.util.*;
import task.*;
import enums.Status;

public class TaskManager {

    private final Map<Integer, Task> registeredTasks = new HashMap<>();
    private final Map<Integer, Epic> registeredEpics = new HashMap<>();
    private final Map<Integer, Subtask> registeredSubtasks = new HashMap<>();

    private int currentID = 0;

    private int getNextID() {
        return currentID++;
    }

    public void addTask(Task task) {
        task.setId(getNextID());
        registeredTasks.put(task.getId(), task);


    }

    public void addEpic(Epic epic) {
        epic.setId(getNextID());
        registeredEpics.put(epic.getId(), epic);

    }

    public void addSubtask(Subtask subtask) {
        subtask.setId(getNextID());
        Epic epic = registeredEpics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
        registeredSubtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epic);


    }

    public Task updateTask(Task task) {
        if (task == null || !registeredTasks.containsKey(task.getId())) {
            return task;
        }
        registeredTasks.replace(task.getId(), task);
        return task;
    }

    public Epic updateEpic(Epic epic) {
        if (epic == null || !registeredEpics.containsKey(epic.getId())){
            return epic;

        }
        Epic oldEpic = registeredEpics.get(epic.getId());
        ArrayList<Subtask> oldEpicSubtaskList = oldEpic.getSubtaskList();
        if (!oldEpicSubtaskList.isEmpty()) {
            for (Subtask subtask : oldEpicSubtaskList) {
                registeredSubtasks.remove(subtask.getId());
            }
        }
        registeredEpics.replace(epic.getId(), epic);
        updateEpicStatus(epic);
        return epic;
    }

    public Subtask updateSubtask(Subtask subtask) {
        if (subtask == null || !registeredSubtasks.containsKey(subtask.getId())){
            return subtask;
        }
        int epicID = subtask.getEpicID();
        Subtask oldSubtask = registeredSubtasks.get(subtask.getId());
        registeredSubtasks.replace(subtask.getId(), subtask);
        Epic epic = registeredEpics.get(epicID);
        ArrayList<Subtask> subtaskList = epic.getSubtaskList();
        subtaskList.remove(oldSubtask);
        subtaskList.add(subtask);
        epic.setSubtaskList(subtaskList);
        updateEpicStatus(epic);
        return subtask;
    }

    public Task getTaskByID(int id) {
        return registeredTasks.get(id);
    }

    public Epic getEpicByID(int id) {
        return registeredEpics.get(id);
    }

    public Subtask getSubtaskByID(int id) {
        return registeredSubtasks.get(id);
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(registeredTasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(registeredEpics.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(registeredSubtasks.values());
    }

    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        return epic.getSubtaskList();
    }

    public void deleteTasks() {
        registeredSubtasks.clear();
    }

    public void deleteEpics() {
        registeredEpics.clear();
        registeredSubtasks.clear();
    }

    public void deleteSubtasks() {
        registeredSubtasks.clear();
        for (Epic epic : registeredEpics.values()) {
            epic.clearSubtasks();
            epic.setStatus(Status.NEW);

        }
    }

    public void deleteTaskByID(int id) {
        registeredTasks.remove(id);
    }

    public void deleteEpicByID(int id) {
        ArrayList<Subtask> epicSubtasks = registeredEpics.get(id).getSubtaskList();
        registeredEpics.remove(id);
        for (Subtask subtask : epicSubtasks) {
            registeredSubtasks.remove(subtask.getId());
        }
    }

    public void deleteSubtaskByID(int id) {
        Subtask subtask = registeredSubtasks.get(id);
        int epicID = subtask.getEpicID();
        registeredSubtasks.remove(id);
        Epic epic = registeredEpics.get(epicID);
        ArrayList<Subtask> subtaskList = epic.getSubtaskList();
        subtaskList.remove(subtask);
        epic.setSubtaskList(subtaskList);
        updateEpicStatus(epic);


    }

    private void updateEpicStatus(Epic epic) {
        int doneCount = 0;
        int newCount = 0;
        ArrayList<Subtask> list = epic.getSubtaskList();

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