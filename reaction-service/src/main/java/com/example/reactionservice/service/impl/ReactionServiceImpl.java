package com.example.reactionservice.service.impl;

import com.example.reactionservice.entities.Reaction;
import com.example.reactionservice.repositories.ReactionRepository;
import com.example.reactionservice.service.ReactionService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReactionServiceImpl implements ReactionService {


    @Autowired
    ReactionRepository reactionRepository;

    // Add Reaction On Post
    @Override
    public void addReactionToPost(String postId, String type) {
        Reaction reaction = new Reaction();
        reaction.setPostId(postId);
        reaction.setType(type);
        reactionRepository.save(reaction);
    }
    // Add Reaction On Comment
    @Override
    public void addReactionToComment(String commentId, String type) {
        Reaction reaction = new Reaction();
        reaction.setCommentId(commentId);
        reaction.setType(type);
        reactionRepository.save(reaction);
    }
    // Delete Reaction On Post
    @Override
    public void deleteReactionFromPost(String postId, String type) {
        reactionRepository.deleteByPostIdAndType(postId, type);
    }
    // Delete Reaction On Comment
    @Override
    public void deleteReactionFromComment(String commentId, String type) {
        reactionRepository.deleteByCommentIdAndType(commentId, type);
    }
    // Get Reaction Count On Post
    @Override
    public long getReactionCountForPost(String postId, String type) {
        return reactionRepository.countByPostIdAndType(postId, type);
    }
    // Get Reaction Count On Comment
    @Override
    public long getReactionCountForComment(String commentId, String type) {
        return reactionRepository.countByCommentIdAndType(commentId, type);
    }
    @Override
    public long getReactionCountForComment(String commentId) {
        return reactionRepository.countByCommentId(commentId);
    }

    @Override
    public long getReactionCountForPost(String postId) {
        return reactionRepository.countByPostId(postId);
    }
}