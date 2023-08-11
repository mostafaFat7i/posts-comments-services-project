package com.example.commentservice.controllers;
import com.example.commentservice.entities.Comment;
import com.example.commentservice.service.CommentService;
import com.example.commentservice.service.impl.CommentServiceImpl;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comments")
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @GetMapping("/comments")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/comments/{id}")
    public Comment getCommentById(@PathVariable String id) {
        return commentService.getCommentById(id);
    }

    @GetMapping("/comments/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable String postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @PutMapping("/comments/{id}")
    public Comment updateComment(@PathVariable String id, @RequestBody Comment comment) {
        return commentService.updateComment(id, comment);
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
    }
}
