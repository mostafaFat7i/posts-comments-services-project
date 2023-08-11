package com.example.commentservice.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.*;


@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Document("comments")
public class Comment {
    @Id
    private String id;
    private String postId;
    private String text;
    private User owner;
}
