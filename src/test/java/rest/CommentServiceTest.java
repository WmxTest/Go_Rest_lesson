package rest;

import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import rest_assured.gorest_co_in.CommentService;
import rest_assured.gorest_co_in.PostService;
import rest_assured.gorest_co_in.dto.Comment;
import rest_assured.gorest_co_in.dto.PostNegative;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.ValueUtils.jsonFileToObject;

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
    @Disabled
    public void deleteComment() {
        Assumptions.assumeTrue(commentId != null);
        CommentService.deleteComment(commentId);
    }

    @ParameterizedTest
    @MethodSource("createCommentDateForNegative")
    public void createNewCommentNegative(int postId, String stringLine, Class<PostNegative[]> cls, Comment commentBody) {
        CommentService.createCommentNegative(postId, stringLine, cls, commentBody);
    }

    @SuppressWarnings("ConstantConditions")
    private Stream<Arguments> createCommentDateForNegative() {
        String filePath = "/comment.json";
        Comment comment1 = jsonFileToObject(filePath, Comment.class);
        Arguments arguments1 = Arguments.of(-2, "HTTP/1.1 422 Unprocessable Entity", PostNegative[].class, comment1);
        Comment comment2 = jsonFileToObject(filePath, Comment.class);
        comment2.setBody(null);
        Arguments arguments2 = Arguments.of(postId, "HTTP/1.1 422 Unprocessable Entity", PostNegative[].class, comment2);
        Comment comment3 = jsonFileToObject(filePath, Comment.class);
        comment3.setBody("");
        Arguments arguments3 = Arguments.of(postId, "HTTP/1.1 422 Unprocessable Entity", PostNegative[].class, comment3);
        Comment comment4 = jsonFileToObject(filePath, Comment.class);
        comment4.setBody("");
        Arguments arguments4 = Arguments.of(-2, "HTTP/1.1 422 Unprocessable Entity", PostNegative[].class, comment3);
        return Stream.of(arguments1, arguments2, arguments3, arguments4);//TODO - to make for checking all object in json array
    }
}