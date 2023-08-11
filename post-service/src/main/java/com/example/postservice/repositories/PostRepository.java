package com.example.postservice.repositories;

import com.example.postservice.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {

}
