package com.example.reactionservice.service;

public interface ReactionService {
    void addReactionToPost(String postId, String type);
    void addReactionToComment(String commentId, String type);
    void deleteReactionFromPost(String postId, String type);
    void deleteReactionFromComment(String commentId, String type);
    long getReactionCountForPost(String postId, String type);
    long getReactionCountForComment(String commentId, String type);
    long getReactionCountForComment(String commentId);
    long getReactionCountForPost(String postId);
}
