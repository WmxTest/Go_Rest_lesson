package rest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import rest_assured.gorest_co_in.dto.Todo;
import rest_assured.gorest_co_in.enums.TodoStatuses;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static rest_assured.gorest_co_in.TodoService.*;
import static rest_assured.gorest_co_in.enums.TodoStatuses.PENDING;

public class TodoServiceTest extends BaseRestTest {

    private static final String TITLE = "changed title";
    private Integer userId;
    private Todo todo;

    @BeforeAll
    public void setUp() {
        userId = getUser().getMId();
    }

//    @Test
//    @Order(1)
//    @DisplayName("check new Todo is created")
//    public void todoShouldBeCreated() {
//        todo = createTodo(userId, PENDING.getStatus());
//        assertEquals(userId, todo.getUserId());
//    }

    @ParameterizedTest
    @Order(2)
    @EnumSource(TodoStatuses.class)
    public void creationTodoWithEnumSource(TodoStatuses statuses) {
        todo = createTodo(userId, statuses.getStatus());
        assertEquals(todo.getStatus(), statuses.getStatus());
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