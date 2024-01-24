package com.example.secretsantaapp.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
    return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
  }

  @GetMapping("/mail/{mail}")
  public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String mail) {
    return new ResponseEntity<>(userService.getUserByEmail(mail), HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
  }

  @GetMapping("/verify/{token}")
  public ResponseEntity<UserDTO> verifyUserEmail(@PathVariable String token) {
    return new ResponseEntity<>(userService.verifyUserEmail(token), HttpStatus.OK);
  }

  @PostMapping()
  public ResponseEntity<UserDTO> createUser(@RequestBody UserCreationDTO userDTO) {
    return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
  }
}
