package rest_assured.gorest_co_in;

import io.restassured.http.ContentType;
import rest_assured.gorest_co_in.dto.Todo;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static utils.DateGenerator.getDateTime;

public class TodoService extends BaseRestService {

    public static Todo createTodo(int userId, String status) {
        Todo body = new Todo();
        body.setId(userId);
        body.setStatus(status);
        body.setTitle("test");
        body.setDueOn(getDateTime("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

        return given()
                .spec(requestSpecification)
                .basePath("/v2/users/" + userId + "/todos")
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .post()
                .then()
                .statusCode(201)
                .extract().as(Todo.class);
    }

    public static List<Todo> getTodos() {
        return Arrays.asList(given()
                .spec(requestSpecification)
                .basePath("/v2/todos")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().as(Todo[].class));
    }

    public static Todo updateTodo(Todo body) {
        return given()
                .spec(requestSpecification)
                .basePath("/v2/todos/" + body.getId())
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .patch()
                .then()
                .statusCode(200)
                .extract().as(Todo.class);
    }
}