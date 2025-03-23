package manager;

import enums.Status;
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
    public void setUp() {
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
        Task copyOfOriginalTask = new Task(originalTask.getId(), originalTask.getName(), originalTask.getDescription(),
                originalTask.getStatus());
        taskManager.addTask(originalTask);
        assertEquals(originalTask.getName(), copyOfOriginalTask.getName(), "Имя не должно быть изменено.");
        assertEquals(originalTask.getDescription(), copyOfOriginalTask.getDescription(),
                "Описание не должно быть изменено.");
        assertEquals(originalTask.getStatus(), copyOfOriginalTask.getStatus(), "Статус не должен быть изменен.");
    }

    @Test
    public void addingEpicShouldNotModifyOriginalEpicObject() {
        Epic originalEpic = new Epic(1, "Оч оригинальное название", "Крутое описание", Status.NEW);
        Epic copyOfOriginalEpic = new Epic(originalEpic.getId(), originalEpic.getName(), originalEpic.getDescription(),
                originalEpic.getStatus());
        taskManager.addEpic(originalEpic);
        assertEquals(originalEpic.getName(), copyOfOriginalEpic.getName(), "Имя не должно быть изменено.");
        assertEquals(originalEpic.getDescription(), copyOfOriginalEpic.getDescription(),
                "Описание не должно быть изменено.");
        assertEquals(originalEpic.getStatus(), copyOfOriginalEpic.getStatus(), "Статус не должен быть изменен.");

    }

    @Test
    public void addingSubtaskShouldNotModifyOriginalSubtaskObject() {
        Epic originalEpic = new Epic(666, "Оч оригинальное название", "Крутое описание", Status.NEW);
        taskManager.addEpic(originalEpic);
        Subtask originalSubtask = new Subtask(1, "Оч оригинальное название", "Крутое описание",
                Status.NEW, originalEpic.getId());
        Subtask copyOfOriginalSubtask = new Subtask(originalSubtask.getId(), originalSubtask.getName(),
                originalSubtask.getDescription(), originalSubtask.getStatus(), originalSubtask.getEpicID());
        taskManager.addSubtask(originalSubtask);
        assertEquals(originalSubtask.getName(), copyOfOriginalSubtask.getName(), "Имя не должно быть изменено.");
        assertEquals(originalSubtask.getDescription(), copyOfOriginalSubtask.getDescription(),
                "Описание не должно быть изменено.");
        assertEquals(originalSubtask.getStatus(), copyOfOriginalSubtask.getStatus(),
                "Статус не должен быть изменен.");
        assertEquals(originalSubtask.getEpicID(), copyOfOriginalSubtask.getEpicID(),
                "Epic ID не должен быть изменен.");
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

    @Test
    public void deleteTaskByIDShouldRemoveTask() {
        Task task = new Task("Test Task", "Test Description");
        taskManager.addTask(task);
        int taskId = task.getId();
        taskManager.deleteTaskByID(taskId);
        assertNull(taskManager.getTaskByID(taskId), "Задача должна быть удалена.");
        assertTrue(taskManager.getTasks().isEmpty(), "Список задач должен быть пуст.");
    }

    @Test
    public void deleteEpicByIDShouldRemoveEpic() {
        Epic epic = new Epic("Test Epic", "Test Description");
        taskManager.addEpic(epic);
        int epicId = epic.getId();
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", epicId);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", epicId);
        taskManager.addSubtask(subtask2);
        taskManager.deleteEpicByID(epicId);
        assertNull(taskManager.getEpicByID(epicId), "Эпик должен быть удален.");
        assertTrue(taskManager.getSubtasks().isEmpty(), "Подзадачи эпика должны быть удалены.");
    }

    @Test
    public void deleteSubtaskByIDShouldRemoveSubtask() {
        Epic epic = new Epic("Test Epic", "Test Description");
        taskManager.addEpic(epic);
        int epicId = epic.getId();
        Subtask subtask = new Subtask("Test Subtask", "Test Description", epicId);
        taskManager.addSubtask(subtask);
        int subtaskId = subtask.getId();
        taskManager.deleteSubtaskByID(subtaskId);
        assertNull(taskManager.getSubtaskByID(subtaskId), "Подзадача должна быть удалена.");
        assertTrue(taskManager.getSubtasks().isEmpty(), "Список подзадач должен быть пуст.");
    }

    @Test
    public void getSubtasksByEpicDirectlyShouldReturnCorrectSubtasks() {
        Epic epic = new Epic("Test Epic", "Test Description");
        taskManager.addEpic(epic);
        int epicId = epic.getId();
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", epicId);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", epicId);
        taskManager.addSubtask(subtask2);
        Epic retrievedEpic = taskManager.getEpicByID(epicId);
        assertNotNull(retrievedEpic, "Эпик не должен быть null.");
        List<Subtask> subtasksForEpic = retrievedEpic.getSubtaskList();
        assertNotNull(subtasksForEpic, "Список подзадач не должен быть null.");
        assertEquals(2, subtasksForEpic.size(), "Список должен содержать 2 подзадачи.");
        assertTrue(subtasksForEpic.contains(subtask1), "Список должен содержать subtask1.");
        assertTrue(subtasksForEpic.contains(subtask2), "Список должен содержать subtask2.");
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