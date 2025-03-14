import enums.Status;
import task.*;
import manager.TaskManager;

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


        Epic roomRedesign = new Epic("Сделать ремонт комнаты", "Постелить новые полы");
        taskManager.addEpic(roomRedesign);
        Subtask roomRedesignStepOne = new Subtask("Снять старые полы", "Аккуратно снять!",
                roomRedesign.getId());
        Subtask roomRedesignStepTwo = new Subtask("Постелить паркет", "Черного оттенка",
                roomRedesign.getId());

        taskManager.addSubtask(roomRedesignStepOne);
        taskManager.addSubtask(roomRedesignStepTwo);
        roomRedesignStepOne.setStatus(Status.DONE);
        System.out.println(roomRedesign);
        taskManager.updateSubtask(roomRedesignStepOne);
        roomRedesignStepTwo.setStatus(Status.DONE);
        taskManager.updateSubtask(roomRedesignStepTwo);

        Epic roomRedesignNew = new Epic(roomRedesign.getId(), "Сделать ремонт прихожей", "Сделать освещение",roomRedesign.getStatus());
        taskManager.updateEpic(roomRedesignNew);


        Subtask roomRedesignStepNewTwo = new Subtask("Купить бра", "Зеленого цвета",
                roomRedesignNew.getId());
        Subtask roomRedesignStepNewOne = new Subtask("Купить люстру", "Энергосберегающего типа",
                roomRedesignNew.getId());
        taskManager.addSubtask(roomRedesignStepNewOne);
        taskManager.addSubtask(roomRedesignStepNewTwo);
        roomRedesignStepNewOne.setStatus(Status.DONE);
        taskManager.updateSubtask(roomRedesignStepNewOne);



        System.out.println(roomRedesign);


    }
}