package com.example.commentservice;

import com.example.commentservice.entities.Comment;
import com.example.commentservice.entities.User;
import com.example.commentservice.repositories.CommentRepository;
import com.example.commentservice.service.CommentService;
import com.example.commentservice.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CommentServiceApplicationTests {


    CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private DirectExchange exchange;

    @Captor
    ArgumentCaptor<Map<String, Object>> commentMapCaptor;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(rabbitTemplate, exchange, commentRepository);
    }

    @Test
    public void getAllCommentsTest() {
        List<Comment> commentList = initializeComments();
        doReturn(commentList).when(commentRepository).findAll();
        List<Comment> expected = commentService.getAllComments();
        assertEquals(commentList.size(), expected.size());
    }

    @Test
    public void saveCommentTest() {
        Comment comment=initializeComments().get(1);
        doReturn(comment).when(commentRepository).save(comment);
        Comment expectedComment=commentService.createComment(comment);
        assertEquals(comment, expectedComment);
    }

    @Test
    void deleteCommentById() {
        Comment comment=initializeComments().get(1);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        commentService.deleteComment(comment.getId());
        verify(commentRepository, times(1)).deleteById(comment.getId());
        verify(commentRepository, times(1)).findById(comment.getId());
    }

    @Test
    public void getCommentById() {
        Comment existingComment=initializeComments().get(1);

        Optional<Comment> optionalComment = Optional.of(existingComment);
        Mockito.when(commentRepository.findById(existingComment.getId())).thenReturn(optionalComment);

        Comment result = commentService.getCommentById(existingComment.getId());
        assertEquals(result, existingComment);
    }

    @Test
    public void getCommentByPostId() {
        Comment existingComment=initializeComments().get(1);

        Optional<Comment> optionalComment = Optional.of(existingComment);
        Mockito.when(commentRepository.findById(existingComment.getPostId())).thenReturn(optionalComment);

        Comment result = commentService.getCommentById(existingComment.getPostId());
        assertEquals(result, existingComment);
    }

    @Test
    public void updatePostById() {
        Comment existingComment=initializeComments().get(1);

        Comment updatedComment=initializeComments().get(1);
        updatedComment.setText("Pranyaaa");
        Optional<Comment> optionalPost = Optional.of(existingComment);
        Mockito.when(commentRepository.findById(existingComment.getId())).thenReturn(optionalPost);
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(updatedComment);

        Comment result = commentService.updateComment(existingComment.getId(), updatedComment);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updatedComment.getText(), result.getText());

    }

    @Test
    void givenStatusCreated_WhenCreateComment_thenEnqueueMessage(){
        Comment createdComment = initializeComments().get(1);
//        commentService.createComment(createdComment);
        verify(rabbitTemplate).convertAndSend(any(),any(),commentMapCaptor.capture());
        Map<String, Object> expectedCommentMap = CommentServiceImpl.convertObjectToHashMap(createdComment);
        expectedCommentMap.put("status","created");
        assertThat(commentMapCaptor.getValue()).isEqualTo(expectedCommentMap);
    }

    @Test
    void givenStatusDeleted_WhenDeleteComment_thenEnqueueMessage(){
        Comment deletedComment = initializeComments().get(1);
        when(commentRepository.findById(deletedComment.getId())).thenReturn(Optional.of(deletedComment));
        commentService.deleteComment(deletedComment.getId());
        verify(rabbitTemplate).convertAndSend(any(),any(),commentMapCaptor.capture());
        Map<String, Object> expectedCommentMap = CommentServiceImpl.convertObjectToHashMap(deletedComment);
        expectedCommentMap.put("status","deleted");
        assertThat(commentMapCaptor.getValue()).isEqualTo(expectedCommentMap);
    }

    @Test
    void createComment_NullComment_ThenThrowException() {
        Comment nullComment = null;
        assertThrows(IllegalArgumentException.class, () -> { commentService.createComment(nullComment); });
    }

    @Test
    void testCreateCommentThrowsException() {
        Comment CreatedComment = initializeComments().get(1);
        doThrow(new RuntimeException("Simulated save exception")).when(commentRepository).save(any(Comment.class));
        assertThrows(RuntimeException.class, () -> commentService.createComment(CreatedComment));
    }


    private List<Comment> initializeComments() {
        return Arrays.asList(
                new Comment("1", "99", "hello", new User(17, "Ahmed")),
                new Comment("2", "00", "London", new User(16, "Ahmed"))
        );
    }




}
