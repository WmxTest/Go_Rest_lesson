package rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import rest_assured.gorest_co_in.dto.User;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static utils.CsvWorker.readValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseRestTest {

    protected static User user;
    protected static Queue<User> usersQueue;

    @SneakyThrows
    public synchronized static void prepareUsers() {
        if (usersQueue == null) {
            FileReader fileReader = new FileReader("src/main/resources/users.CSV");
            List<User> users = readValue(fileReader, User.class, 0);
            usersQueue = new LinkedList<>(users);
        }
    }

    @SneakyThrows
    public synchronized static User getUser() {
        return usersQueue.poll();
    }

    @BeforeAll
    public void beforeAll() {
        prepareUsers();
    }
}