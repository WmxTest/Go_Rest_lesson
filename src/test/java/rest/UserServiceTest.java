package rest;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import rest_assured.gorest_co_in.dto.User;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static rest_assured.gorest_co_in.UserService.*;

public class UserServiceTest extends BaseRestTest {

    private boolean isUserDeleted = false;
    private User user;

    @Order(1)
    @Test
    public void createNewUser() {
        user = createUser();
    }

    @Order(2)
    @Test
    public void userShouldExist() {
        assumeTrue(user != null);
        assertTrue(isUserExists(user.getMId()));
    }

    @Order(2)
    @Test
    public void update_User() {
        assumeTrue(user != null);
        updateUser(user);
    }

    @Order(3)
    @Test
    public void delete_User() {
        assumeTrue(user != null);
        deleteUser(user);
        isUserDeleted = true;
    }

    @Order(4)
    @Test
    public void userShouldNotExist() {
        assumeTrue(isUserDeleted);
        assertFalse(isUserExists(user.getMId()));
    }

    @ParameterizedTest
    public void createUserNegativeCase(){

    }
}