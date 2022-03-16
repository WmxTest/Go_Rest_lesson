package rest;

import org.junit.jupiter.api.*;
import rest_assured.gorest_co_in.PostService;
import rest_assured.gorest_co_in.UserService;

public class PostServiceTest extends BaseRestTest {
    private Integer userId;
    private Integer postId;

    @BeforeAll
    public void setUp() {
        userId = getUser().getMId();
        System.out.println(userId);
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
        Assertions.assertTrue(PostService.isPostExist(postId));
    }
}