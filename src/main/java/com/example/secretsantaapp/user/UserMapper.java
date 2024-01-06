package com.example.secretsantaapp.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User convertToEntity(UserCreationDTO userDTO){
        return new User(userDTO.getName(), userDTO.getPassword(), userDTO.getEmail());
    }

    public UserDTO convertToDto(User user){
        return new UserDTO(user.getName(), user.getEmail());
    }
}
