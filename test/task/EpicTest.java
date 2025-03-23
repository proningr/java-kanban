package task;
import enums.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    @Test
    public void EpicsShodeBeEqualIfIdEquals() {
        Epic epic1 = new Epic(1, "Прочитать книгу", "читать с пониманием", Status.NEW);
        Epic epic2 = new Epic(1, "Прочитать газету", "читать с пониманием",
                Status.DONE);
        assertEquals(epic1, epic2,
                "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}