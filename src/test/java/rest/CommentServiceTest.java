package rest;

import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.*;
import rest_assured.gorest_co_in.CommentService;
import rest_assured.gorest_co_in.PostService;
import rest_assured.gorest_co_in.dto.Comment;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommentServiceTest extends BaseRestTest {
    private Integer postId;
    private Integer commentId;
    private Comment editedBody;

    @BeforeAll
    public void setUp() {
        int userId = getUser().getMId();
        postId = PostService.createPost(userId).getId();

        editedBody = new Comment();
        editedBody.setEmail("1234@abcd.com");
        editedBody.setName("Comment name was changed.");
        editedBody.setBody("New text should be here.");
    }

    @Test
    @Order(1)
    public void createNewComment() {
        Assumptions.assumeTrue(postId != null);
        commentId = CommentService.createComment(postId).getId();
    }

    @Test
    @Order(2)
    public void checkPublishedComment() {
        Assumptions.assumeTrue(commentId != null);
        assertTrue(CommentService.isCommentExist(postId, commentId));
    }

    @Test
    @Order(3)
    public void getPostComments() {
        Assumptions.assumeTrue(postId != null);
        CommentService.retrievePostComments(postId);
    }

    @SuppressWarnings("rawtypes")
    @Test
    @Order(4)
    public void changeComment() {
        Assumptions.assumeTrue(commentId != null);
        ResponseBody body = CommentService.editComment(commentId, editedBody);
        Assertions.assertEquals(editedBody.getEmail(), body.path("email"));
    }

    @Test
    @Order(5)
    public void deleteComment() {
        Assumptions.assumeTrue(commentId != null);
        CommentService.deleteComment(commentId);
    }
}