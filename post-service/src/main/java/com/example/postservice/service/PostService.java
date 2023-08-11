package com.example.postservice.service;

import com.example.postservice.entities.Post;

import java.util.List;
import java.util.Map;
import org.springframework.amqp.core.Message;


public interface PostService {

    public Post getPostById(String id);

    public Post savePost(Post post);

    public List<Post> getAllPosts();

    public Post updatePostById(String id, Post updatedPost);

    public Post sharePost(Post sharedPost);

    public boolean deletePostById(String id);

//    public void updateCommentsCount(Map<String, Object> commentMap);

    void processMessage(Message message);

}
