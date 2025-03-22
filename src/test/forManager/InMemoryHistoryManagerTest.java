package test.forManager;

import enums.Status;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }
    @Test
    public void getHistoryShouldReturnListOf0Tasks() {
        for (int i = 0; i < 61; i++) {
            taskManager.addTask(new Task("Name Task:  " + i, "Some description:  " + i));
        }
        List<Task> list = taskManager.getHistory();
        assertEquals(0, list.size(), "Неверное количесество при добавлении задач " +
                "-- getHistory должен вернуть size() = 0 ");
    }
    @Test
    public void getHistoryShouldReturnListOf10Tasks() {
        for (int i = 0; i < 61; i++) {
            taskManager.addTask(new Task("Name Task:  " + i, "Some description:  " + i));
        }
        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            taskManager.getTaskByID(task.getId());
        }
        List<Task> list = taskManager.getHistory();
        assertEquals(10, list.size(), "Неверное количество элементов в списке ");
    }
    @Test
    public void getHistoryShouldReturnTasksWithCorrectLastTaskName() {

        int numberOfTasks = 61;
        for (int i = 0; i < numberOfTasks; i++) {
            taskManager.addTask(new Task("Name Task:  " + i, "Some description:  " + i));
        }
        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            taskManager.getTaskByID(task.getId());
        }
        List<Task> history = taskManager.getHistory();
        Task lastTask = history.get(history.size() - 1);
        String expectedLastName = "Name Task:  " + (numberOfTasks - 1);
        assertEquals(expectedLastName, lastTask.getName(), "Неверное имя последней задачи в списке истории");
    }
    @Test
    public void getHistoryShouldReturnOldTaskAfterUpdate() {
        Task testTask = new Task("Задача для замены", "Тестирую что при заменей данной " +
                "задачи - все корректно");
        taskManager.addTask(testTask);
        taskManager.getTaskByID(testTask.getId());
        taskManager.updateTask(new Task(testTask.getId(), "UPD Задача",
                "Обновили задачу", Status.DONE));
        List<Task> tasks = taskManager.getHistory();
        Task oldTask = tasks.getFirst();
        assertEquals(testTask.getName(), oldTask.getName(), "В истории не сохранилась старая версия задачи");
        assertEquals(testTask.getDescription(), oldTask.getDescription(),
                "В истории не сохранилась старая версия задачи");
        assertEquals(testTask.getId(), oldTask.getId(),
                "ID не совпадает!");
    }
    @Test
    public void getHistoryShouldReturnOldEpicAfterUpdate() {
        Epic testEpic = new Epic("Эпик для замены", "Тестирую что при заменей данного " +
                "эпика - все корректно");
        taskManager.addEpic(testEpic);
        taskManager.getEpicByID(testEpic.getId());
        taskManager.updateEpic(new Epic(testEpic.getId(), "UPD Эпик",
                "Обновили эпик", Status.DONE));
        List<Task> epics = taskManager.getHistory();
        Epic oldEpic = (Epic) epics.getFirst();
        assertEquals(testEpic.getName(), oldEpic.getName(), "В истории не сохранилась старая версия задачи");
        assertEquals(testEpic.getDescription(), oldEpic.getDescription(),
                "В истории не сохранилась старая версия задачи");
        assertEquals(testEpic.getId(), oldEpic.getId(),
                "ID не совпадает!");
    }
    @Test
    public void getHistoryShouldReturnOldSubtaskAfterUpdate() {
        Epic anyEpic = new Epic("Какой то эпик", "Какое то описание");
        taskManager.addEpic(anyEpic);
        Subtask anySubtask = new Subtask("Какая то подзадача", "Какое то описание",
                anyEpic.getId());
        taskManager.addSubtask(anySubtask);
        taskManager.getSubtaskByID(anySubtask.getId());
        taskManager.updateSubtask(new Subtask(anySubtask.getId(), "Новое имя",
                "новое описание", Status.IN_PROGRESS, anyEpic.getId()));
        List<Task> subtasks = taskManager.getHistory();
        Subtask oldSubtask = (Subtask) subtasks.getFirst();
        assertEquals(anySubtask.getName(), oldSubtask.getName(),
                "В истории не сохранилась старая версия эпика");
        assertEquals(anySubtask.getDescription(), oldSubtask.getDescription(),
                "В истории не сохранилась старая версия эпика");
        assertEquals(oldSubtask.getId(), anySubtask.getId(),
                "ID не совпадает!");
    }
}