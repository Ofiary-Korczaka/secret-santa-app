package com.example.secretsantaapp.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationDTO {
    private String name;
    private String email;
    private String password;
}
