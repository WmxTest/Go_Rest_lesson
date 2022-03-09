package rest_assured.gorest_co_in;

import io.restassured.http.ContentType;
import rest_assured.gorest_co_in.dto.User;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static utils.EmailGeneration.generateRandomEmail;

public class UserServiceClone {

    //body хардкод
    public static User getUserId_1() {
        return given()
                .header("Authorization", "Bearer 0bdd1ce53ac0f81f5fd7ef18892fae1dc0a10043e495b636a4ac891440f89220")
                .contentType(ContentType.JSON)
                .when()
                .body("{\"name\":\"Ten DDD\", \"gender\":\"male\", \"email\":\"" + generateRandomEmail() + "\", \"status\":\"active\"}")
                .post("https://gorest.co.in/public/v2/users")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .as(User.class);
    }

    public static User getUserId_2() {

        Map<String, Object> body = new HashMap<>();
        body.put("name", "Bob");
        body.put("gender", "male");
        body.put("email", generateRandomEmail());
        body.put("status", "active");

        return given()
                .header("Authorization", "Bearer 0bdd1ce53ac0f81f5fd7ef18892fae1dc0a10043e495b636a4ac891440f89220")
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .post("https://gorest.co.in/public/v2/users")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .as(User.class);
    }

    public static void main(String[] args) {
        System.out.println(getUserId_2());
    }
}
