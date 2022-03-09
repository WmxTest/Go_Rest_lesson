package rest_assured.gorest_co_in;

import io.restassured.http.ContentType;
import rest_assured.gorest_co_in.dto.Post;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.given;

public class PostService extends BaseRestService {

    public static Post createPost(int userId) {
        Post body = new Post();
        body.setBody("123456789 Hello world");
        body.setTitle("My first post");
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
                        .extract().as(Post[].class)
        );
    }

    public static boolean isPostExist(int postId) {
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

}
