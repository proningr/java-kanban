package task;

import enums.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubtaskTest {

    @Test
    public void SubtaskBeEqualIfIdEquals() {
        Subtask subtask1 = new Subtask(616, "Сходить в церковь", "Сходить в Вск", Status.NEW, 5);
        Subtask subtask2 = new Subtask(616, "Сходить на рок концерт", "Взять костет", Status.DONE, 5);
        assertEquals(subtask1, subtask2,
                "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}