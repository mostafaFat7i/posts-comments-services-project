package com.example.commentservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@Document("Users")
public class User {
    private int userId;
    private String userName;
}
