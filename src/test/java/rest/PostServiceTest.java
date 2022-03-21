package rest;

import org.junit.jupiter.api.*;
import rest_assured.gorest_co_in.PostService;

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
}