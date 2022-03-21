package rest_assured.gorest_co_in;

import io.restassured.http.ContentType;
import rest_assured.gorest_co_in.dto.Post;
import utils.ValueUtils;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.given;

public class PostService extends BaseRestService {

    @SuppressWarnings("all")
    public static Post createPost(int userId) {
        Post body = ValueUtils.jsonFileToObject("/post.json", Post.class);
        body.setUserId(userId);

        return given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .basePath("/v2/users/" + userId + "/posts")
                .body(body)
                .post()
                .then()
                .assertThat().statusCode(201)
                .extract().as(Post.class);
    }

    public static List<Post> getAllPosts() {
        return Arrays.asList(
                given()
                        .spec(requestSpecification)
                        .basePath("/v2/posts")
                        .get()
                        .then()
                        .assertThat().statusCode(200)
                        .extract().as(Post[].class));
    }

    public static boolean isPostExists(int postId) {
        try {
            getAllPosts()
                    .stream()
                    .filter(post -> post.getId().equals(postId))
                    .findAny()
                    .orElseThrow(() -> new NoSuchElementException(String.format("Post doesn't exist: %d", postId)));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static Post updatePost(int postId, Post body) {
        return given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .basePath("/v2/posts/" + postId)
                .body(body)
                .patch()
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(Post.class);
    }
}