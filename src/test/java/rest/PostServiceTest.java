package rest;

import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import rest_assured.gorest_co_in.PostService;
import rest_assured.gorest_co_in.dto.Post;
import utils.ValueUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class PostServiceTest extends BaseRestTest {
    private Integer userId;
    private Integer postId;

    @BeforeAll
    public void setUp() {
        userId = getUser().getMId();

    }

    @Test
    @Order(1)
    public void postShouldBeCreated() {
        Assumptions.assumeTrue(userId != null);
        postId = PostService.createPost(userId).getId();
    }

    @Test
    public void checkPublishedPost() {
        Assumptions.assumeTrue(postId != null);
        Assertions.assertTrue(PostService.isPostExists(postId));
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    public void checkNegativeCases(Map<String, Object> testObject) {
        ResponseBody responseBody = PostService.createPostNegativeCase((Post) testObject.get("requestBody"), (String) testObject.get("statusMessage"));
        Assertions.assertEquals(testObject.get("response"), responseBody.asString());
    }

    public static Stream<Map<String, Object>> testDataProvider() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();

        Post requestBody1 = ValueUtils.jsonFileToObject("/post.json", Post.class);
        requestBody1.setUserId(123456789);
        Post requestBody2 = ValueUtils.jsonFileToObject("/post.json", Post.class);
        requestBody2.setUserId(getUser().getMId());
        requestBody2.setTitle(null);
        map1.put("requestBody", requestBody1);
        map2.put("requestBody", requestBody2);
        map1.put("statusMessage", "HTTP/1.1 422 Unprocessable Entity");
        map2.put("statusMessage", "HTTP/1.1 422 Unprocessable Entity");
        map1.put("response", "[{\"field\":\"user\",\"message\":\"must exist\"}]");
        map2.put("response", "[{\"field\":\"title\",\"message\":\"can't be blank\"}]");
        return Stream.of(map1, map2);
    }
}