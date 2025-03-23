import enums.Status;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager inMemoryTaskManag = Managers.getDefault();
        Task learning = new Task("Выучить java", "Пройти курс на STEPIC");
        inMemoryTaskManag.addTask(learning);

        Task learningToModify = new Task(learning.getId(), "Выучить java", "Пройти курс на Я.Практикум",
                Status.IN_PROGRESS);
        Task learningModified = inMemoryTaskManag.updateTask(learningToModify);
        System.out.println(learningModified);

        Task learning2 = new Task("Выучить python", "Пройти курс на Coursera");
        inMemoryTaskManag.addTask(learning2);
        learning2.setStatus(Status.IN_PROGRESS);

        Epic roomRedesign = new Epic("Сделать ремонт комнаты", "Постелить новые полы");
        inMemoryTaskManag.addEpic(roomRedesign);
        Subtask roomRedesignStepOne = new Subtask("Снять старые полы", "Аккуратно снять!",
                roomRedesign.getId());
        Subtask roomRedesignStepTwo = new Subtask("Постелить паркет", "Черного оттенка",
                roomRedesign.getId());

        inMemoryTaskManag.addSubtask(roomRedesignStepOne);
        inMemoryTaskManag.addSubtask(roomRedesignStepTwo);
        roomRedesignStepOne.setStatus(Status.IN_PROGRESS);
        System.out.println(roomRedesign);
        inMemoryTaskManag.updateSubtask(roomRedesignStepOne);
        roomRedesignStepTwo.setStatus(Status.DONE);
        inMemoryTaskManag.updateSubtask(roomRedesignStepTwo);
        inMemoryTaskManag.deleteSubtasks();

        Epic roomRedesignNew = new Epic(roomRedesign.getId(), "Сделать ремонт прихожей", "Сделать освещение", roomRedesign.getStatus());
        inMemoryTaskManag.updateEpic(roomRedesignNew);

        Subtask roomRedesignStepNewFour = new Subtask("Купить ковер", "Серого цвета",
                roomRedesignNew.getId());

        Subtask roomRedesignStepNewFive = new Subtask("Купить дверь", "Белого цвета",
                roomRedesignNew.getId());
        inMemoryTaskManag.addSubtask(roomRedesignStepNewFive);
        inMemoryTaskManag.addSubtask(roomRedesignStepNewFour);

        Subtask updateRedesignStepFive = new Subtask(roomRedesignStepNewFive.getId(), "Купить дверь", "Черного цвета", Status.DONE, roomRedesignNew.getId());
        inMemoryTaskManag.updateSubtask(updateRedesignStepFive);

        Subtask updateRedesignStepNewFour = new Subtask(roomRedesignStepNewFour.getId(), "Купить ковер", "Синего цвета", Status.IN_PROGRESS, roomRedesignNew.getId());
        inMemoryTaskManag.updateSubtask(updateRedesignStepNewFour);
        inMemoryTaskManag.deleteSubtaskByID(updateRedesignStepNewFour.getId());
        Epic testEpic = new Epic("Протестироваться", "Создать новый эпик");
        inMemoryTaskManag.addEpic(testEpic);
        Epic newTestEpic = new Epic("ЕщЕ один пустой эпик", "Статус должен быть NEW");
        inMemoryTaskManag.addEpic(newTestEpic);
        System.out.println(roomRedesign);

    }
}