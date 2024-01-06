package com.example.secretsantaapp.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserCreationDTO {

    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;
}
