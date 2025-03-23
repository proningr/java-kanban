package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private static HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void addShouldNotAddNullTask() {
        historyManager.add(null);
        List<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "История не должна содержать null задачу.");
    }

    @Test
    void getHistoryShouldReturnEmptyListInitially() {
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не должна быть null.");
        assertTrue(history.isEmpty(), "История должна быть пустой при создании.");
    }

    @Test
    void addShouldAddTaskToHistory() {
        Task task1 = new Task("Task 1", "Description 1");
        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История должна содержать 1 задачу.");
        assertEquals(task1, history.get(0), "Задача должна быть добавлена в историю.");
    }

    @Test
    void getHistoryShouldReturnACopyOfTheList() {
        Task task1 = new Task("Task 1", "Description 1");
        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();
        history.clear();
        List<Task> historyAfterClear = historyManager.getHistory();
        assertEquals(1, historyAfterClear.size(), "История не должна быть изменена после очистки копии.");
        assertEquals(task1, historyAfterClear.get(0), "Задача должна остаться в исходной истории.");
    }

    @Test
    void addShouldMaintainOrderOfTasks() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        historyManager.add(task1);
        historyManager.add(task2);
        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать 2 задачи.");
        assertEquals(task1, history.get(0), "Первая задача должна быть task1.");
        assertEquals(task2, history.get(1), "Вторая задача должна быть task2.");
    }

    @Test
    public void getHistoryShouldReturnListOf10Tasks() {
        for (int i = 0; i < 12; i++) {
            historyManager.add(new Task("Name Task:  " + i, "Some description:  " + i));
        }
        List<Task> tasks = historyManager.getHistory();
        assertEquals(10, tasks.size(), "Неверное количество элементов в списке ");
    }

    @Test
    public void getHistoryShouldReturnTasksWithCorrectLastTaskName() {
        int numberOfTasks = 11;
        for (int i = 0; i < numberOfTasks; i++) {
            historyManager.add(new Task("Name Task:  " + i, "Some description:  " + i));
        }
        List<Task> history = historyManager.getHistory();
        Task lastTask = history.get(history.size() - 1);
        String expectedLastName = "Name Task:  " + (numberOfTasks - 1);
        assertEquals(expectedLastName, lastTask.getName(), "Неверное имя последней задачи в списке истории");
    }
}