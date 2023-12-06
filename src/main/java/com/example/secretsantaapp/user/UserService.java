package com.example.secretsantaapp.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService{

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserDTO createUser(UserCreationDTO userDTO){
        User user = userMapper.convertToEntity(userDTO);
        userRepository.save(user);
        return userMapper.convertToDto(user);
    }

    public UserDTO getUserById(String id){
        User user = userRepository.findById(id).orElseThrow();
        return userMapper.convertToDto(user);
    }

    //TODO - add filtering users by email
    public UserDTO getUserByEmail(String email){
        return null;
    }

    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    //TODO - add deleting users


}
