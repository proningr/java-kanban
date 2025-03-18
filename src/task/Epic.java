package task;

import enums.Status;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Subtask> subTaskArray = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public void addSubtask(Subtask subtask) {
        subTaskArray.add(subtask);
    }

    public void clearSubtasks() {
        subTaskArray.clear();
    }

    public void setSubtaskList(ArrayList<Subtask> subtaskList) {
        this.subTaskArray = subtaskList;
    }

    public ArrayList<Subtask> getSubtaskList() {
        return subTaskArray;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name= " + getName() + '\'' +
                ", description = " + getDescription() + '\'' +
                ", id=" + getId() +
                ", subtaskList.size = " + subTaskArray.size() +
                ", status = " + getStatus() +
                '}';
    }
}
