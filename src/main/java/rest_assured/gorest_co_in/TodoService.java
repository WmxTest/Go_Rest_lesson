package rest_assured.gorest_co_in;

import io.restassured.http.ContentType;
import rest_assured.gorest_co_in.dto.Todo;

import static io.restassured.RestAssured.given;
import static utils.DateGenerator.getLocalDateTime;

public class TodoService extends BaseRestService {

    public static void createTodo(int userId, String status) {
        Todo body = new Todo();
        body.setId(userId);
        body.setStatus(status);
        body.setTitle("test");
        body.setDueOn(getLocalDateTime("YYYY-MM-ddEHH:mm:ss.nnnx"));

        given()
                .spec(requestSpecification)
                .basePath("/v2/users/" + userId + "/todos")
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .post()
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .as(Todo.class);
    }

    public static void main(String[] args) {
        createTodo(3443, "completed");
    }
}