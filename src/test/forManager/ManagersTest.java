package test.forManager;

import manager.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultShouldCreateNewInMemoryClass() {
        assertInstanceOf(InMemoryTaskManager.class, Managers.getDefault());
    }

    @Test
    void getDefaultShouldCreateNewInMemoryHistoryClass() {
        assertInstanceOf(InMemoryHistoryManager.class, Managers.getDefaultHistory());
    }
}