package rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import rest_assured.gorest_co_in.dto.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static rest_assured.gorest_co_in.UserService.getUsers;
import static utils.CsvWorker.readValue;
import static utils.CsvWorker.writeValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseRestTest {

    private static final String FILE_PATH = "src/main/resources/users.CSV";
    private static Queue<User> usersQueue;

    @SneakyThrows
    public synchronized static void prepareUsers() {
        if (usersQueue == null) {
            writeValue(new FileWriter(FILE_PATH), getUsers());
            List<User> users = readValue(new FileReader(FILE_PATH), User.class, 0);
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