package com.example.reactionservice.entities;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;


@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Data
@Document(collection = "reactions")
public class Reaction {
    @Id
    private String id;
    private String postId;
    private String commentId;
    private String type;
}
