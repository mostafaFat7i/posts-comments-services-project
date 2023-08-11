package com.example.postservice.service.impl;

import com.example.postservice.entities.Post;
import com.example.postservice.repositories.PostRepository;
import com.example.postservice.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConversionException;

import java.io.IOException;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    private ObjectMapper objectMapper;

    @Override
    public Post getPostById(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }

    public LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }

    @Override
    public Post savePost(Post post) {

        post.setDate(getCurrentDate());
        return postRepository.save(post);
    }


    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    @Override
    public Post updatePostById(String id, Post updatedPost) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            post.get().setTitle(updatedPost.getTitle());
            post.get().setContent(updatedPost.getContent());
            post.get().setCommentsCount(updatedPost.getCommentsCount());
            return postRepository.save(post.get());
        } else {
            return null;
        }
    }

    @Override
    public boolean deletePostById(String id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            postRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Post sharePost(Post sharedPost) {
        Optional<Post> oldPost = postRepository.findById(sharedPost.getPostId());
        if (oldPost.isPresent()) {
            Post newPost =new Post();
            newPost.setOwner(sharedPost.getOwner());
            newPost.setTitle(oldPost.get().getTitle());
            newPost.setContent(oldPost.get().getContent());
            newPost.setDate(getCurrentDate());
            return postRepository.save(newPost);
        } else {
            return null;
        }
    }

    @RabbitListener(queues = "${myapp.rabbitmq.queueName}")
    @Override
    public void processMessage(Message message) {
        try {
            Map<String, Object> commentMap = convertMessagePayload(message);
            Post post = getPostById(((String) commentMap.get("postId")));
            Object status = commentMap.get("status");
            if ("created".equals(status)) {
                post.setCommentsCount(post.getCommentsCount()+1);
            } else if ("deleted".equals(status)) {
                post.setCommentsCount(post.getCommentsCount()-1);
            }
            updatePostById(post.getPostId(),post);
        } catch (IOException e) {
        }
    }

    private Map<String, Object> convertMessagePayload(Message message) throws IOException {
        byte[] body = message.getBody();
        if (body == null) {
            throw new MessageConversionException("Message body is null");
        }
        return objectMapper.readValue(body, Map.class);
    }
}
