package com.example.secretsantaapp.potentialgift;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@Setter
@Document
@NoArgsConstructor
public class PotentialGift {
    @Id
    private String id;
    private String email;
    private String description;
    private String name;
}