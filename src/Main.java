public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task learning = new Task("Выучить java", "Пройти курс на STEPIC");
        Task learningAdd= taskManager.addTask(learning);
        System.out.println(learningAdd);

        Task learningToModify = new Task(learning.getId(), "Выучить java", "Пройти курс на Я.Практкум",
                Status.IN_PROGRESS);
        Task cleaningModified = taskManager.updateTask(learningToModify);
        System.out.println(cleaningModified);


        Epic roomRedesign = new Epic("Сделать ремонт комнаты", "Постелить новые полы");
        taskManager.addEpic(roomRedesign);
        System.out.println(roomRedesign);
        Subtask roomRedesignStepOne = new Subtask("Снять старые полы", "Аккуратно снять!",
                roomRedesign.getId());
        Subtask roomRedesignStepTwo = new Subtask("Постелить паркет", "Черного оттенка",
                roomRedesign.getId());
        taskManager.addSubtask(roomRedesignStepOne);
        taskManager.addSubtask(roomRedesignStepTwo);
        System.out.println(roomRedesign);
        roomRedesignStepTwo.setStatus(Status.DONE);
        taskManager.updateSubtask(roomRedesignStepTwo);
        System.out.println(roomRedesign);
        System.out.println("Точка останова");

    }
}