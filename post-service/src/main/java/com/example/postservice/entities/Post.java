package com.example.postservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Posts")
public class Post {

    @Id
    private String postId;
    private int commentsCount;
    private String title;
    private String content;
    private User owner;
    private LocalDateTime date;
}
