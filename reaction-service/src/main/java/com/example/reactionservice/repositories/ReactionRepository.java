package com.example.reactionservice.repositories;

import com.example.reactionservice.entities.Reaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReactionRepository extends MongoRepository<Reaction, String> {

    long countByPostIdAndType(String postId, String type);
    long countByCommentIdAndType(String commentId, String type);
    long countByCommentId(String commentId);
    long countByPostId(String postId);
    void deleteByPostIdAndType(String postId, String type);
    void deleteByCommentIdAndType(String commentId, String type);
}
