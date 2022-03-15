package rest;

import org.junit.jupiter.api.*;
import rest_assured.gorest_co_in.PostService;
import rest_assured.gorest_co_in.UserService;

public class PostServiceTest extends BaseRestTest {
    private Integer user_Id;
    private Integer post_id;

    @BeforeAll
    public void setUp() {
        user_Id = getUser().getMId();
    }

    @Test
    @Order(1)
    public void postShouldBeCreated() {
        Assumptions.assumeTrue(user_Id != null);
        post_id = PostService.createPost(user_Id).getId();
    }

    @Test
    public void checkPublishedPost() {
        Assumptions.assumeTrue(post_id != null);
        Assertions.assertTrue(PostService.isPostExist(post_id));
    }
}