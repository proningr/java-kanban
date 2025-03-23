package manager;

import task.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int MAX_ITEM_VIEW = 10;
    private final List<Task> historyList = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (historyList.size() == MAX_ITEM_VIEW) {
            historyList.removeFirst();
        }
        historyList.add(task);
    }
    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyList);
    }
}