package com.example.secretsantaapp.group;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@ToString
@Getter
@Setter
@Document
public class Group {
    @Id
    private String id;
    private String name;
    private Set<String> emails;
    private Boolean isStarted;
    private Boolean isFinished;
}
