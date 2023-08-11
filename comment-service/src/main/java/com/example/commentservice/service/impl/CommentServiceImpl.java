package com.example.commentservice.service.impl;
import com.example.commentservice.entities.Comment;
import com.example.commentservice.repositories.CommentRepository;
import com.example.commentservice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.commentservice.configuration.RabbitMQConfiguration.COMMENT_ROUTING_KEY;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange exchange;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(Comment createdComment) {
        if (createdComment == null) { throw new IllegalArgumentException("Comment cannot be null."); }
        try {
            createdComment = commentRepository.save(createdComment);
            Map<String, Object> commentMap = convertObjectToHashMap(createdComment);
            commentMap.put("status", "created");
            rabbitTemplate.convertAndSend(exchange.getName(), COMMENT_ROUTING_KEY, commentMap);
            return createdComment;
        } catch (Exception e) {
             throw new RuntimeException("Failed to send comment to RabbitMQ.", e);
        }
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(String id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment updateComment(String id, Comment updatedComment) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            comment.setText(updatedComment.getText());
            comment.setPostId(updatedComment.getPostId());
            return commentRepository.save(comment);
        }
        return null;
    }

    @Override
    public void deleteComment(String id) {
        Comment deletedComment = getCommentById(id);
        Map<String, Object> commentMap = convertObjectToHashMap(deletedComment);
        commentMap.put("status","deleted");
        rabbitTemplate.convertAndSend(exchange.getName(),COMMENT_ROUTING_KEY,commentMap);
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getCommentsByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }

    public static Map<String, Object> convertObjectToHashMap(Object object) {
        Map<String, Object> hashMap = new HashMap<>();
        // Retrieve object's properties using reflection and map them to the HashMap
        if (object != null) {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    hashMap.put(field.getName(), value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return hashMap;
    }
}
