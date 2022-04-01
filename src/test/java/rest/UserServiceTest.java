package rest;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import rest_assured.gorest_co_in.dto.PostNegative;
import rest_assured.gorest_co_in.dto.User;

import java.util.stream.Stream;

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
    @MethodSource("prepareTestData")
    public void createUserNegativeCase(User body, String statusLine, Class<PostNegative[]> cls) {
        createUser(body, statusLine, cls);
    }

    public static Stream<Arguments> prepareTestData() {
        User user1 = getUser();
        User user2 = getUser();
        user2.setMEmail("Test@test");
        return Stream.of(Arguments.of(user1, "HTTP/1.1 422 Unprocessable Entity", PostNegative[].class), Arguments.of(user2, "HTTP/1.1 422 Unprocessable Entity", PostNegative[].class));
    }
}