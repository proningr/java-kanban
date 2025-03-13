import java.util.*;

public class TaskManager {

    private final Map<Integer, Task> registeredTasks = new HashMap<>();
    private final Map<Integer, Epic> registeredEpics = new HashMap<>();
    private final Map<Integer, Subtask> registeredSubtasks = new HashMap<>();

    private int currentID = 0;

    private int getNextID() {
        return currentID++;
    }

    public Task addTask(Task task) {
        task.setId(getNextID());
        registeredTasks.put(task.getId(), task);
        return task;
    }

    public Epic addEpic(Epic epic) {
        epic.setId(getNextID());
        registeredEpics.put(epic.getId(), epic);
        return epic;
    }

    public Subtask addSubtask(Subtask subtask) {
        subtask.setId(getNextID());
        Epic epic = registeredEpics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
        registeredSubtasks.put(subtask.getId(), subtask);
        return subtask;
    }

    public Task updateTask(Task task) {
        Integer taskID = task.getId();
        if (taskID == null || !registeredTasks.containsKey(taskID)) {
            return null;
        }
        registeredTasks.replace(taskID, task);
        return task;
    }

    public Epic updateEpic(Epic epic) {
        Integer epicID = epic.getId();
        if (epicID == null || !registeredEpics.containsKey(epicID)) {
            return null;
        }
        Epic oldEpic = registeredEpics.get(epicID);
        ArrayList<Subtask> oldEpicSubtaskList = oldEpic.getSubtaskList();
        if (!oldEpicSubtaskList.isEmpty()) {
            for (Subtask subtask : oldEpicSubtaskList) {
                registeredSubtasks.remove(subtask.getId());
            }
        }
        registeredEpics.replace(epicID, epic);
        ArrayList<Subtask> newEpicSubtaskList = epic.getSubtaskList();
        if (!newEpicSubtaskList.isEmpty()) {
            for (Subtask subtask : newEpicSubtaskList) {
                registeredSubtasks.put(subtask.getId(), subtask);
            }
        }
        return epic;
    }

    public Subtask updateSubtask(Subtask subtask) {
        Integer subtaskID = subtask.getId();
        if (subtaskID == null || !registeredSubtasks.containsKey(subtaskID)) {
            return null;
        }
        int epicID = subtask.getEpicID();
        Subtask oldSubtask = registeredSubtasks.get(subtaskID);
        registeredSubtasks.replace(subtaskID, subtask);
        Epic epic = registeredEpics.get(epicID);
        ArrayList<Subtask> subtaskList = epic.getSubtaskList();
        subtaskList.remove(oldSubtask);
        subtaskList.add(subtask);
        epic.setSubtaskList(subtaskList);
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

    }


}