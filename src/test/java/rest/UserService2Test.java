package rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import rest_assured.gorest_co_in.dto.User;
import static org.junit.jupiter.api.Assertions.*;
import static rest_assured.gorest_co_in.UserService.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserService2Test extends BaseRestTest {

    @Test
    public void userShouldBeCreated() {
        createUser();
    }

    @Test
    public void userShouldExist() {
        assertTrue(isUserExists(getUser().getMId()));
    }

    @Test
    public void update_User() {
        updateUser(getUser());
    }

    @Test
    public void delete_User() {
        User user = getUser();
        deleteUser(user);
        assertFalse(isUserExists(user.getMId()));
    }
}