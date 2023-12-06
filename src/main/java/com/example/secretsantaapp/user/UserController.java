package com.example.secretsantaapp.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value="/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/mail/{mail}")
    public ResponseEntity<UserDTO>  getUserByEmail(@PathVariable String mail){
        return new ResponseEntity<>(userService.getUserByEmail(mail), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

   @PostMapping()
   public ResponseEntity<UserDTO>  createUser(@RequestBody UserCreationDTO userDTO){
        //TODO - add password strength check
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

}
