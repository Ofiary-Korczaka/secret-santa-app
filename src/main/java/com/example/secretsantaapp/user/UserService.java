package com.example.secretsantaapp.user;

import static com.example.secretsantaapp.util.CommonUtil.validateEmail;

import com.example.secretsantaapp.common.exception.GenericErrorException;
import com.example.secretsantaapp.component.mail.MailNotificationService;
import com.example.secretsantaapp.user.exception.UserAlreadyExistsException;
import com.example.secretsantaapp.user.exception.UserNotFoundException;
import com.example.secretsantaapp.util.CommonUtil;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder bCryptPasswordEncoder;
  private final MailNotificationService mailNotificationService;

  @Autowired
  public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder bCryptPasswordEncoder, MailNotificationService mailNotificationService) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.mailNotificationService = mailNotificationService;
  }

  public UserDTO createUser(UserCreationDTO userCreationDTO) {
    Optional<User> userCheck = userRepository.findByEmail(userCreationDTO.getEmail());
    if (userCheck.isPresent()) {
      throw new UserAlreadyExistsException("Email is already taken!");
    }

    if (!validateEmail(userCreationDTO.getEmail())) {
      throw new GenericErrorException("Provided email is not valid!");
    }

    User user = userMapper.convertToEntity(userCreationDTO);

    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setEmailVerificationToken(CommonUtil.generateUniqueString());
    userRepository.save(user);

    UserDTO userDTO = userMapper.convertToDto(user);

    mailNotificationService.sendEmailConfirmation(userDTO, user.getEmailVerificationToken());

    return userDTO;
  }

  public boolean isUserEmailVerified(String email) {
    User user = userFromRepoByEmail(email);
    return user.getEmailVerified() != null;
  }

  public UserDTO verifyUserEmail(String token) {
    Optional<User> userOptional = userRepository.findByEmailVerificationToken(token);
    if (userOptional.isEmpty()) {
      throw new GenericErrorException("Invalid token");
    }

    User user = userOptional.get();
    user.setEmailVerificationToken(null);
    user.setEmailVerified(new Date());

    return userMapper.convertToDto(userRepository.save(user));
  }

  public UserDTO getUserById(String id) {
    User user = userRepository.findById(id).orElse(null);
    if (user == null) {
      throw new UserNotFoundException("User not found with id: " + id);
    }
    return userMapper.convertToDto(user);
  }

  public UserDTO getUserByEmail(String email) {
    User user = userFromRepoByEmail(email);
    return userMapper.convertToDto(user);
  }

  public List<UserDTO> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream().map(userMapper::convertToDto).toList();
  }

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    return userFromRepoByEmail(username);
  }

  // TODO - add deleting users
  private User userFromRepoByEmail(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User with name:" + email + " does not exist");
    }
    return user.get();
  }
}
