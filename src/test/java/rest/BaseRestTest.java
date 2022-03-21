package rest;

import org.junit.jupiter.api.*;
import rest_assured.gorest_co_in.dto.*;

import java.util.LinkedList;
import java.util.Queue;

import static rest_assured.gorest_co_in.UserService.getUsers;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseRestTest {

    private static Queue<User> users;

    @BeforeAll
    public static void beforeAll() {
        prepareData();
    }

    private static synchronized void prepareData() {
        if (users == null) {
            users = new LinkedList<>(getUsers());
        }
    }

    private static synchronized void prepareData2() {

    }

    protected static synchronized User getUser() {
        return users.poll();
    }
}