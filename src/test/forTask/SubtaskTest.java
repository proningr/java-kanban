package test.forTask;
import task.*;

import org.junit.jupiter.api.Test;
import enums.Status;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    public void SubtaskBeEqualIfIdEquals() {
        Subtask subtask1 = new Subtask(616, "Сходить в церковь", "Сходить в Вск", Status.NEW, 5);
        Subtask subtask2 = new Subtask(616, "Сходить на рок концерт", "Взять костет", Status.DONE, 5);
        assertEquals(subtask1, subtask2,
                "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}