package rest_assured.gorest_co_in;

import io.restassured.http.ContentType;
import rest_assured.gorest_co_in.dto.User;

import java.util.*;

import static io.restassured.RestAssured.given;
import static utils.EmailGenerator.generateRandomEmail;

public class UserService extends BaseRestService {

    public static User createUser() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Bob");
        body.put("gender", "male");
        body.put("email", generateRandomEmail());
        body.put("status", "active");

        return given()
                .spec(requestSpecification)
                .basePath("/v2/users")
                .contentType(ContentType.JSON)
                .body(body)
                .post()
                .then()
                .statusCode(201)
                .extract().as(User.class);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static User updateUser(User user) {
        user.setMName("Alice");

        return given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(user)
                .put("/v2/users/{userId}", user.getMId())
                .then()
                .statusCode(200)
                .extract().as(User.class);
    }

    public static void deleteUser(User user) {
        given()
                .spec(requestSpecification)
                .delete("/v2/users/{userId}", user.getMId())
                .then()
                .statusLine("HTTP/1.1 204 No Content");
    }

    public static List<User> getUsers() {
        return Arrays.asList(given()
                .spec(requestSpecification)
                .basePath("/v2/users")
                .get()
                .then()
                .statusCode(200)
                .extract().as(User[].class));
    }

    public static boolean isUserExists(int userId) {
        try {
            getUsers()
                    .stream()
                    .filter(user -> user.getMId().equals(userId))
                    .findAny()
                    .orElseThrow(() -> new NoSuchElementException(String.format("User doesn't exist: %d", userId)));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static List<String[]> getUsersList() {
        List<String[]> users = new ArrayList<>();
        getUsers().forEach(user -> {
            String[] arr = new String[5];
            arr[0] = String.valueOf(user.getMId());
            arr[1] = user.getMEmail();
            arr[2] = user.getMGender();
            arr[3] = user.getMName();
            arr[4] = user.getMStatus();
            users.add(arr);
        });
        return users;
    }
}