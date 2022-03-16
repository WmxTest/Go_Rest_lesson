package rest;

import org.junit.jupiter.api.*;
import rest_assured.gorest_co_in.dto.*;

import java.util.LinkedList;
import java.util.Queue;

import static rest_assured.gorest_co_in.UserService.getUsers;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseRestTest {

    protected static User user;
    private static Queue<User> users;

    @BeforeAll
    public void beforeAll() {
        prepareDate();
    }

    private static synchronized void prepareDate() {
        if (users == null) {
            users = new LinkedList<>(getUsers());
        }
    }

    protected static synchronized User getUser() {
        return users.poll();
    }
}