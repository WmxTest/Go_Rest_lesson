package rest;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import rest_assured.gorest_co_in.CommentService;
import rest_assured.gorest_co_in.PostService;
import rest_assured.gorest_co_in.UserService;

public class CommentServiceTest extends BaseRestTest {
    private Integer userID;
    private Integer postID;
    private Integer commentId;

    @BeforeAll
    public void setUP() {
        userID = UserService.createUser().getMId();
        postID = PostService.createPost(userID).getId();
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
    @Order(4)
    public void changeComment() {
        Assumptions.assumeTrue(commentId != null);
        CommentService.editComment(commentId);
    }

    @Test
    @Order(5)
    public void deleteComment() {
        Assumptions.assumeTrue(commentId != null);
        CommentService.deleteComment(commentId);
    }
}