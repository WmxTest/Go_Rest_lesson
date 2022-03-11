package rest;

import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.*;
import rest_assured.gorest_co_in.CommentService;
import rest_assured.gorest_co_in.PostService;
import rest_assured.gorest_co_in.UserService;
import rest_assured.gorest_co_in.dto.Comment;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceTest extends BaseRestTest {
    private Integer postID;
    private Integer commentId;
    private Comment editedBody;

    @BeforeAll
    public void setUP() {
        int userID = UserService.createUser().getMId();
        postID = PostService.createPost(userID).getId();

        editedBody = new Comment();
        editedBody.setEmail("1234@abcd.com");
        editedBody.setName("Comment name was changed.");
        editedBody.setBody("New text should be here.");
    }

    @Test
    @Order(1)
    public void createNewComment() {
        Assumptions.assumeTrue(postID != null);
        commentId = CommentService.createComment(postID).getId();
    }

    @Test
    @Order(2)
    public void checkPublishedComment() {
        Assumptions.assumeTrue(commentId != null);
        CommentService.isCommentExist(postID, commentId);
    }

    @Test
    @Order(3)
    public void getPostComments() {
        Assumptions.assumeTrue(postID != null);
        CommentService.retrievePostComments(postID);
    }

    @Test
    @SuppressWarnings("all")
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