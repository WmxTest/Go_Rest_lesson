package rest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import rest_assured.gorest_co_in.UserService;
import rest_assured.gorest_co_in.dto.Todo;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static rest_assured.gorest_co_in.TodoService.*;
import static rest_assured.gorest_co_in.enums.TodoStatuses.PENDING;

public class TodoServiceTest extends BaseRestTest {

    private static final String TITLE = "changed title";
    private Integer user_Id;
    private Todo todo;

    @BeforeAll
    public void setUp() {
        user_Id = getUser().getMId();
    }

    @Test
    @Order(1)
    @DisplayName("check new Todo is created")
    public void todoShouldBeCreated() {
        todo = createTodo(user_Id, PENDING.getStatus());
        assertEquals(user_Id, todo.getUserId());
    }

    @Test
    public void findTodoInList() {
        AtomicBoolean contains = new AtomicBoolean(false);
        getTodos()
                .forEach(i -> {
                    if (i.getId().equals(todo.getId())) {
                        contains.set(true);
                    }
                });
        assertTrue(contains.get());
    }

    @Test
    public void updateCreatedTodo() {
        todo.setTitle(TITLE);
        assertEquals(TITLE, updateTodo(todo).getTitle());
    }
}