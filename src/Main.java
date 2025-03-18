import enums.Status;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task learning = new Task("Выучить java", "Пройти курс на STEPIC");
        taskManager.addTask(learning);

        Task learningToModify = new Task(learning.getId(), "Выучить java", "Пройти курс на Я.Практикум",
                Status.IN_PROGRESS);
        Task learningModified = taskManager.updateTask(learningToModify);
        System.out.println(learningModified);

        Task learning2 = new Task("Выучить python", "Пройти курс на Coursera");
        taskManager.addTask(learning2);
        learning2.setStatus(Status.IN_PROGRESS);

        Epic roomRedesign = new Epic("Сделать ремонт комнаты", "Постелить новые полы");
        taskManager.addEpic(roomRedesign);
        Subtask roomRedesignStepOne = new Subtask("Снять старые полы", "Аккуратно снять!",
                roomRedesign.getId());
        Subtask roomRedesignStepTwo = new Subtask("Постелить паркет", "Черного оттенка",
                roomRedesign.getId());

        taskManager.addSubtask(roomRedesignStepOne);
        taskManager.addSubtask(roomRedesignStepTwo);
        roomRedesignStepOne.setStatus(Status.IN_PROGRESS);
        System.out.println(roomRedesign);
        taskManager.updateSubtask(roomRedesignStepOne);
        roomRedesignStepTwo.setStatus(Status.DONE);
        taskManager.updateSubtask(roomRedesignStepTwo);
        taskManager.deleteSubtasks();

        Epic roomRedesignNew = new Epic(roomRedesign.getId(), "Сделать ремонт прихожей", "Сделать освещение", roomRedesign.getStatus());
        taskManager.updateEpic(roomRedesignNew);

        Subtask roomRedesignStepNewFour = new Subtask("Купить ковер", "Серого цвета",
                roomRedesignNew.getId());

        Subtask roomRedesignStepNewFive = new Subtask("Купить дверь", "Белого цвета",
                roomRedesignNew.getId());
        taskManager.addSubtask(roomRedesignStepNewFive);
        taskManager.addSubtask(roomRedesignStepNewFour);

        Subtask updateRedesignStepFive = new Subtask(roomRedesignStepNewFive.getId(), "Купить дверь", "Черного цвета", Status.DONE, roomRedesignNew.getId());
        taskManager.updateSubtask(updateRedesignStepFive);

        Subtask updateRedesignStepNewFour = new Subtask(roomRedesignStepNewFour.getId(), "Купить ковер", "Синего цвета", Status.DONE, roomRedesignNew.getId());
        taskManager.updateSubtask(updateRedesignStepNewFour);
        Epic testEpic = new Epic("Протестироваться", "Создать новый эпик");
        taskManager.addEpic(testEpic);
        Epic newTestEpic = new Epic("ЕщЕ один пустой эпик", "Статус должен быть NEW");
        taskManager.addEpic(newTestEpic);
        System.out.println(roomRedesign);
    }
}