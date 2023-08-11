package com.example.postservice.controllers;

import com.example.postservice.entities.Post;
import com.example.postservice.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostServiceImpl postServiceOperation;

    @PostMapping("/api/posts")
    public ResponseEntity<Post> saveReqPost(@RequestBody Post post) {
        return new ResponseEntity<>(postServiceOperation.savePost(post), HttpStatus.CREATED);
    }
    @PostMapping("/api/posts/share")
    public ResponseEntity<Post> shareReqPost(@RequestBody Post shared_post) {
        return new ResponseEntity<>(postServiceOperation.sharePost(shared_post), HttpStatus.CREATED);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> getAllReqPost() {
        return new ResponseEntity<>(postServiceOperation.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<Post> getReqPostById(@PathVariable String id) {
        return new ResponseEntity<>(postServiceOperation.getPostById(id), HttpStatus.OK);
    }


    @PutMapping("/api/posts/{id}")
    public ResponseEntity<Post> updateReqPostById(@PathVariable String id, @RequestBody Post updatedPost) {
        return new ResponseEntity<>(postServiceOperation.updatePostById(id, updatedPost), HttpStatus.OK);
    }


    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<Void> deleteReqPostById(@PathVariable int id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
