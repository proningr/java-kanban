package test.forTask;
import task.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import enums.Status;

class TaskTest {

    @Test
    public void tasksShouldBeEqualIfIdEquals() {
        Task task1 = new Task(666, "Сходить на тренировку", "Взять спортивную обувь", Status.NEW);
        Task task2 = new Task(666, "Сходить в магазин", "Купить водку", Status.DONE);
        assertEquals(task1, task2,
                "Ошибка! Экземпляры класса Task должны быть равны друг другу, если равен их id;");
    }
}