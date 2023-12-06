package com.example.secretsantaapp.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;


@ToString
@Getter
@Setter
@Document
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;

    public User(String name, String password, String email){
        this.name = Objects.requireNonNull(name);
        //TODO - add password encryption
        this.password = password;
        //TODO - add email check regex
        this.email = Objects.requireNonNull(email);
    }
}
