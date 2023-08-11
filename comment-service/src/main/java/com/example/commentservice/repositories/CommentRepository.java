package com.example.commentservice.repositories;
import com.example.commentservice.entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String>{

    List<Comment> findByPostId(String postId);
}
