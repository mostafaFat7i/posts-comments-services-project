package com.example.reactionservice.controllers;

import com.example.reactionservice.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reactions")
public class ReactionController {

    @Autowired
    ReactionService reactionService;

    @PostMapping("/posts/{postId}/{type}")
    public void addReactionToPost(@PathVariable String postId, @PathVariable String type) {
            reactionService.addReactionToPost(postId, type);
    }
    @PostMapping("/comments/{commentId}/{type}")
    public void addReactionToComment(@PathVariable String commentId, @PathVariable String type) {
        reactionService.addReactionToComment(commentId, type);
    }
    @GetMapping("/posts/{postId}/{type}/count")
    public long getReactionCountForPost(@PathVariable String postId, @PathVariable String type) {
        return reactionService.getReactionCountForPost(postId, type);
    }
    @GetMapping("/comments/{commentId}/{type}/count")
    public long getReactionCountForComment(@PathVariable String commentId, @PathVariable String type) {
        return reactionService.getReactionCountForComment(commentId, type);
    }
    @DeleteMapping("/posts/{postId}/{type}")
    public void deleteReactionFromPost(@PathVariable String postId, @PathVariable String type) {
        reactionService.deleteReactionFromPost(postId, type);
    }
    @DeleteMapping("/comments/{commentId}/{type}")
    public void deleteReactionFromComment(@PathVariable String commentId, @PathVariable String type) {
        reactionService.deleteReactionFromComment(commentId, type);
    }

    @GetMapping("/comments/{commentId}/count")
    public long getReactionCountForComment(@PathVariable String commentId) {
        return reactionService.getReactionCountForComment(commentId);
    }

    @GetMapping("/posts/{postId}/count")
    public long getReactionCountForPost(@PathVariable String postId) {
        return reactionService.getReactionCountForPost(postId);
    }
}
