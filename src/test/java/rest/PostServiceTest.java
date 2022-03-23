package rest;

import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import rest_assured.gorest_co_in.PostService;
import rest_assured.gorest_co_in.dto.Post;
import utils.ValueUtils;

import java.util.stream.Stream;

@SuppressWarnings("all")
public class PostServiceTest extends BaseRestTest {
    private Integer userId;
    private Integer postId;

    public static Stream<Arguments> testDataProvider() {
        Post requestBody1 = ValueUtils.jsonFileToObject("/post.json", Post.class);
        requestBody1.setUserId(123456789);

        Post requestBody2 = ValueUtils.jsonFileToObject("/post.json", Post.class);
        requestBody2.setUserId(getUser().getMId());
        requestBody2.setTitle(null);

        return Stream.of(
                Arguments.of(requestBody1, "HTTP/1.1 422 Unprocessable Entity", "[{\"field\":\"user\",\"message\":\"must exist\"}]"),
                Arguments.of(requestBody2, "HTTP/1.1 422 Unprocessable Entity", "[{\"field\":\"title\",\"message\":\"can't be blank\"}]"));
    }

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
    public void checkNegativeCases(Post requestBody, String statusMessage, String response) {
        ResponseBody responseBody = PostService.createPostNegativeCase(requestBody, statusMessage);
        Assertions.assertEquals(response, responseBody.asString());
    }
}

/*
1)
Map<String,String> map = new HashMap<>();
map.put("field","user");
map.put("message","must exist");

Map<String, String> responseMap = responseBody.jsonPath().getMap("[0]");
map.equals(responseMap); --> will return true

2)
[
    {
        "field":"user",
        "message":"must exist"

    },
    {
        "field":"title",
        "message":"can't be blank"

    }
]

responseBody.path("[0]").toString() will be extracted -->
   {
        "field":"user",
        "message":"must exist"
    }
*/