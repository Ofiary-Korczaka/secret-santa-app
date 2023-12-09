package com.example.secretsantaapp.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDTO createUser(UserCreationDTO userDTO){
        Optional<User> userCheck = userRepository.findByEmail(userDTO.getEmail());
        if(userCheck.isPresent()){
            throw new RuntimeException("Email is already taken!");
        }
        User user = userMapper.convertToEntity(userDTO);
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return userMapper.convertToDto(user);
    }

    public UserDTO getUserById(String id){
        User user = userRepository.findById(id).orElseThrow();
        return userMapper.convertToDto(user);
    }

    public UserDTO getUserByEmail(String email){
        User user = userFromRepoByEmail(email);
        return userMapper.convertToDto(user);
    }

    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userFromRepoByEmail(username);
        Set<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),authorities);
    }
    //TODO - add deleting users
    private User userFromRepoByEmail(String email) throws UsernameNotFoundException{
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User with name:"+email+" does not exist");
        }
        return user.get();
    }

}
