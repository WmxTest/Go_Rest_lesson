package rest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import rest_assured.gorest_co_in.dto.User;
import utils.CsvWorker;

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
            String valueSource = System.getProperty("test.source");
            if (valueSource != null && valueSource.equalsIgnoreCase("csv")) {
                users = new LinkedList<>(CsvWorker.getObjectList(User.class, 0, "src/main/resources/users.CSV"));
            } else {
                users = new LinkedList<>(getUsers());
            }
        }
    }

    protected static synchronized User getUser() {
        return users.poll();
    }
}