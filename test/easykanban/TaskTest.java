package easykanban;

import org.junit.Test;
import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void testCheckUserName_Valid() {
        EasyKanban app = new EasyKanban();
        assertTrue("Username should be correctly formatted.", app.checkUserName("kyI_1"));
    }

    @Test
    public void testCheckUserName_Invalid() {
        EasyKanban app = new EasyKanban();
        assertFalse("Username should be incorrectly formatted.", app.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexity_Valid() {
        EasyKanban app = new EasyKanban();
        assertTrue("Password meets complexity requirements.", app.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid() {
        EasyKanban app = new EasyKanban();
        assertFalse("Password does not meet complexity requirements.", app.checkPasswordComplexity("password"));
    }

    @Test
    public void testLoginUser_Success() {
        EasyKanban app = new EasyKanban();
        app.registerUser("kyI_1", "Ch&&sec@ke99!");
        assertTrue("Login should be successful.", app.loginUser("kyI_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUser_Failure() {
        EasyKanban app = new EasyKanban();
        app.registerUser("kyI_1", "Ch&&sec@ke99!");
        assertFalse("Login should fail with incorrect credentials.", app.loginUser("wrongUser", "wrongPassword"));
    }
}
