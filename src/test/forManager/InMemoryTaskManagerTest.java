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

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    void addNewTask() {
        taskManager.addTask(new Task("Test NEW TASK", "Test NEW DESC"));
        final Task savedTask = taskManager.getTaskByID(0);
        assertNotNull(savedTask, "Задача не найдена.");
        final List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void addNewEpic() {
        taskManager.addEpic(new Epic("Test NEW EPIC", "Test NEW DESC"));
        final Epic savedEpic = taskManager.getEpicByID(0);
        assertNotNull(savedEpic, "Эпик не найден.");
        final List<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
    }

    @Test
    void addNewSubtask() {
        taskManager.addEpic(new Epic("Test NEW EPIC", "Test NEW DESC"));
        taskManager.addSubtask(new Subtask("Test NEW SUB_1", "Test NEW DESC", 0));
        taskManager.addSubtask(new Subtask("Test NEW SUB_2", "Test NEW DESC", 0));
        taskManager.addSubtask(new Subtask("Test NEW SUB_3", "Test NEW DESC", 0));
        taskManager.addSubtask(new Subtask("Test NEW SUB_4", "Test NEW DESC", 0));
        taskManager.addSubtask(new Subtask("Test NEW SUB_5", "Test NEW DESC", 0));
        taskManager.addSubtask(new Subtask("Test NEW SUB_6", "Test NEW DESC", 0));
        final Subtask savedSubtask = taskManager.getSubtaskByID(6);
        assertNotNull(savedSubtask, "Подзадач не найден.");
        final List<Subtask> subtasks = taskManager.getSubtasks();
        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(6, subtasks.size(), "Неверное количество подзадач.");
    }

    @Test
    public void addingTaskShouldNotModifyOriginalTaskObject() {
        Task originalTask = new Task(1, "Оч оригинальное название", "Крутое описание", Status.NEW);
        Task copyOfOriginalTask = new Task(originalTask.getId(), originalTask.getName(), originalTask.getDescription(), originalTask.getStatus());
        taskManager.addTask(originalTask);
        assertEquals(originalTask.getName(), copyOfOriginalTask.getName(), "Имя не должно быть изменено.");
        assertEquals(originalTask.getDescription(), copyOfOriginalTask.getDescription(), "Описание не должно быть изменено.");
        assertEquals(originalTask.getStatus(), copyOfOriginalTask.getStatus(), "Статус не должен быть изменен.");
    }

    @Test
    public void addingEpicShouldNotModifyOriginalEpicObject() {
        Epic originalEpic = new Epic(1, "Оч оригинальное название", "Крутое описание", Status.NEW);
        Epic copyOfOriginalEpic = new Epic(originalEpic.getId(), originalEpic.getName(), originalEpic.getDescription(), originalEpic.getStatus());
        taskManager.addEpic(originalEpic);
        assertEquals(originalEpic.getName(), copyOfOriginalEpic.getName(), "Имя не должно быть изменено.");
        assertEquals(originalEpic.getDescription(), copyOfOriginalEpic.getDescription(), "Описание не должно быть изменено.");
        assertEquals(originalEpic.getStatus(), copyOfOriginalEpic.getStatus(), "Статус не должен быть изменен.");

    }

    @Test
    public void addingSubtaskShouldNotModifyOriginalSubtaskObject() {
        Epic originalEpic = new Epic(666, "Оч оригинальное название", "Крутое описание", Status.NEW);
        taskManager.addEpic(originalEpic);
        Subtask originalSubtask = new Subtask(1, "Оч оригинальное название", "Крутое описание", Status.NEW, originalEpic.getId());
        Subtask copyOfOriginalSubtask = new Subtask(originalSubtask.getId(), originalSubtask.getName(), originalSubtask.getDescription(), originalSubtask.getStatus(), originalSubtask.getEpicID());
        taskManager.addSubtask(originalSubtask);
        assertEquals(originalSubtask.getName(), copyOfOriginalSubtask.getName(), "Имя не должно быть изменено.");
        assertEquals(originalSubtask.getDescription(), copyOfOriginalSubtask.getDescription(), "Описание не должно быть изменено.");
        assertEquals(originalSubtask.getStatus(), copyOfOriginalSubtask.getStatus(), "Статус не должен быть изменен.");
        assertEquals(originalSubtask.getEpicID(), copyOfOriginalSubtask.getEpicID(), "Epic ID не должен быть изменен.");
    }

    @Test
    public void updateTaskShouldReturnTaskWithTheSameId() {
        final Task expected = new Task("имя", "описание");
        taskManager.addTask(expected);
        final Task updatedTask = new Task(expected.getId(), "новое имя", "новое описание", Status.DONE);
        final Task actual = taskManager.updateTask(updatedTask);
        assertEquals(expected, actual, "Вернулась задачи с другим id");
    }

    @Test
    public void updateEpicShouldReturnEpicWithTheSameId() {
        final Epic expected = new Epic("имя", "описание");
        taskManager.addEpic(expected);
        final Epic updatedEpic = new Epic(expected.getId(), "новое имя", "новое описание", Status.DONE);
        final Epic actual = taskManager.updateEpic(updatedEpic);
        assertEquals(expected, actual, "Вернулся эпик с другим id");
    }

    @Test
    public void updateSubtaskShouldReturnSubtaskWithTheSameId() {
        final Epic epic = new Epic("имя", "описание");
        taskManager.addEpic(epic);
        final Subtask expected = new Subtask("имя", "описание", epic.getId());
        taskManager.addSubtask(expected);
        final Subtask updatedSubtask = new Subtask(expected.getId(), "новое имя", "новое описание",
                Status.DONE, epic.getId());
        final Subtask actual = taskManager.updateSubtask(updatedSubtask);
        assertEquals(expected, actual, "Вернулась подзадача с другим id");
    }

    @Test
    public void deleteTasksShouldReturnEmptyList() {
        taskManager.addTask(new Task("Тестовая задача", "придуманное описание"));
        taskManager.addTask(new Task("Тестовая задача номер 2", "дурацкое описание"));
        taskManager.deleteTasks();
        List<Task> tasks = taskManager.getTasks();
        assertTrue(tasks.isEmpty(), "После удаления задач список должен быть пуст.");
    }

    @Test
    public void deleteEpicsShouldReturnEmptyList() {
        taskManager.addEpic(new Epic("Тестовая задача какая то", "Я устал писать автотесты"));
        taskManager.deleteEpics();
        List<Epic> epics = taskManager.getEpics();
        assertTrue(epics.isEmpty(), "После удаления эпиков список должен быть пуст.");
    }

    @Test
    public void deleteSubtasksShouldReturnEmptyList() {
        Epic originalEpic = new Epic("ЧТо то сделать", "Нужно что то сделать");
        taskManager.addEpic(originalEpic);
        taskManager.addSubtask(new Subtask("бла бла ", "бла описание",
                originalEpic.getId()));
        taskManager.addSubtask(new Subtask("-----", "--",
                originalEpic.getId()));
        taskManager.addSubtask(new Subtask("++++", "+",
                originalEpic.getId()));

        taskManager.deleteSubtasks();
        List<Subtask> subtasks = taskManager.getSubtasks();
        assertTrue(subtasks.isEmpty(), "После удаления подзадач список должен быть пуст.");
    }


}