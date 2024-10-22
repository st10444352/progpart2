package easykanban;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EasyKanbanTest {
    private EasyKanban kanban;

    @Before
    public void setUp() {
        kanban = new EasyKanban();
    }

    //Username is correctly formatted.
    @Test
    public void testUsernameCorrectlyFormatted() {
        String username = "kyI_1";
        assertTrue(kanban.checkUserName(username));
    }

    //Username is incorrectly formatted.
    @Test
    public void testUsernameIncorrectlyFormatted() {
        String username = "kyle!!!!!!!";
        assertFalse(kanban.checkUserName(username));
    }

    //The password complexity requirements are met.
    @Test
    public void testPasswordMeetsComplexity() {
        String password = "Ch&&sec@ke99!";
        assertTrue(kanban.checkPasswordComplexity(password));
    }

    //The password complexity requirements are not met.
    @Test
    public void testPasswordDoesNotMeetComplexity() {
        String password = "password";
        assertFalse(kanban.checkPasswordComplexity(password));
    }

    //Login was successful.
    @Test
    public void testLoginSuccessful() {
        kanban.registerUser("kyI_1", "Ch&&sec@ke99!");
        assertTrue(kanban.loginUser("kyI_1", "Ch&&sec@ke99!"));
    }

    //Login has failed.
    @Test
    public void testLoginFailed() {
        kanban.registerUser("kyI_1", "Ch&&sec@ke99!");
        assertFalse(kanban.loginUser("wrongUser", "wrongPass"));
    }

    //Task Description >50 characters.
    @Test
    public void testTaskDescriptionLength() {
        assertTrue(kanban.checkTaskDescription("A valid task description"));
        assertFalse(kanban.checkTaskDescription("This task description is definitely more than fifty characters long and should fail."));
    }

    //TotalHours Correctly accumulated and displayed.
@Test
public void testTotalHoursAccumulation() {
    kanban.tasks.add(new Task("Task1", 0, "Description1", "Dev1", 10, "To Do"));
    kanban.totalHours += 10;
    assertEquals(10, kanban.returnTotalHours());

    kanban.tasks.add(new Task("Task2", 1, "Description2", "Dev2", 20, "Doing"));
    kanban.totalHours += 20;
    assertEquals(30, kanban.returnTotalHours());

    kanban.tasks.clear();
    kanban.totalHours = 0;
    kanban.tasks.add(new Task("Task3", 0, "Task Description", "Dev1", 10, "To Do"));
    kanban.tasks.add(new Task("Task4", 1, "Task Description", "Dev2", 12, "Doing"));
    kanban.tasks.add(new Task("Task5", 2, "Task Description", "Dev3", 55, "Done"));
    kanban.tasks.add(new Task("Task6", 3, "Task Description", "Dev4", 11, "To Do"));
    kanban.tasks.add(new Task("Task7", 4, "Task Description", "Dev5", 1, "Doing"));

    for (Task task : kanban.tasks) {
        kanban.totalHours += task.getDuration();
    }

    assertEquals(89, kanban.returnTotalHours());
}


    //TaskID is correct and runs well.
    @Test
    public void testTaskIDGeneration() {
        Task task1 = new Task("Login Feature", 0, "Create Login to authenticate users", "Robyn Harrison", 8, "To Do");
        assertEquals("LO:0:SON", task1.printTaskDetails().split("\n")[5].split(": ")[1]);

        Task task2 = new Task("Add Task Feature", 1, "Create Add Task feature to add task users", "Mike Smith", 10, "Doing");
        assertEquals("AD:1:ITH", task2.printTaskDetails().split("\n")[5].split(": ")[1]);
    }
}
