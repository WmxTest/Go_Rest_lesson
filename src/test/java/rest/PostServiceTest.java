package rest;

import io.restassured.response.ResponseBody;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import rest_assured.gorest_co_in.PostService;
import rest_assured.gorest_co_in.dto.Post;
import rest_assured.gorest_co_in.dto.PostNegative;
import utils.ValueUtils;

import java.util.stream.Stream;

public class PostServiceTest extends BaseRestTest {
    private Integer userId;
    private Post postBody;

    @SuppressWarnings("ConstantConditions")
    public static Stream<Arguments> testDataProvider() {
        Post requestBody1 = ValueUtils.jsonFileToObject("/post.json", Post.class);
        requestBody1.setUserId(123456789);

        Post requestBody2 = ValueUtils.jsonFileToObject("/post.json", Post.class);
        requestBody2.setUserId(getUser().getMId());
        requestBody2.setTitle(null);

        Post requestBody3 = ValueUtils.jsonFileToObject("/post.json", Post.class);
        requestBody3.setUserId(getUser().getMId());
        requestBody3.setBody(null);

        return Stream.of(
                Arguments.of(requestBody1, "HTTP/1.1 422 Unprocessable Entity", "[{\"field\":\"user\",\"message\":\"must exist\"}]"),
                Arguments.of(requestBody2, "HTTP/1.1 422 Unprocessable Entity", "[{\"field\":\"title\",\"message\":\"can't be blank\"}]"),
                Arguments.of(requestBody3, "HTTP/1.1 422 Unprocessable Entity", "[{\"field\":\"body\",\"message\":\"can't be blank\"}]"));
    }

    @BeforeAll
    public void setUp() {
        userId = getUser().getMId();
    }

    @Test
    @Order(1)
    public void postShouldBeCreated() {
        Assumptions.assumeTrue(userId != null);
        postBody = PostService.createPost(userId);
    }

    @Test
    @Order(2)
    public void checkPublishedPost() {
        Assumptions.assumeTrue(postBody != null);
        Assertions.assertTrue(PostService.isPostExists(postBody.getId()));
    }

    @SuppressWarnings("rawtypes")
    @ParameterizedTest
    @MethodSource("testDataProvider")
    @Order(2)
    public void checkNegativeCases(Post requestBody, String statusMessage, String response) {
        ResponseBody responseBody = PostService.createPostNegativeCase(requestBody, statusMessage);
        Assertions.assertEquals(response, responseBody.asString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"changed", "123345", "\""})
    @Order(2)
    public void checkPostIsUpdated(String title) {
        Assumptions.assumeTrue(postBody != null);
        String body = "111";
        postBody.setTitle(title);
        postBody.setBody(body);
        Post responseBody = PostService.updatePost(postBody, Post.class, 200);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(responseBody.getTitle()).isEqualTo(title);
        softAssertions.assertThat(responseBody.getBody()).isEqualTo(body);
        softAssertions.assertAll();
    }

    @ParameterizedTest
    @Order(2)
//    @CsvFileSource(files = "src/main/resources/posts.CSV", numLinesToSkip = 1)
    @CsvFileSource(resources = "/posts.CSV", numLinesToSkip = 1)
    public void checkPostIsUpdatedCSVProvider(String title, String body) {
        Assumptions.assumeTrue(postBody != null);
        postBody.setTitle(title);
        postBody.setBody(body);
        Post responseBody = PostService.updatePost(postBody, Post.class, 200);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(responseBody.getTitle()).isEqualTo(title);
        softAssertions.assertThat(responseBody.getBody()).isEqualTo(body);
        softAssertions.assertAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", ""})
    @Order(2)
    public void checkPostIsUpdatedNegative(String title) {
        Assumptions.assumeTrue(postBody != null);
        postBody.setTitle(title);
        PostNegative[] responseBody = PostService.updatePost(postBody, PostNegative[].class, 422);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(responseBody[0].getField()).isEqualTo("title");
        softAssertions.assertThat(responseBody[0].getMessage()).isEqualTo("can't be blank");
        softAssertions.assertAll();
    }

    @Test
    @Order(3)
    public void checkPostDeleted() {
        Assumptions.assumeTrue(postBody != null);
        int postId = postBody.getId();
        PostService.deletePost(postId);
        Assertions.assertFalse(PostService.isPostExists(postId));
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