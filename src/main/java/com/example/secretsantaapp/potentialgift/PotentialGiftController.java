package com.example.secretsantaapp.potentialgift;

import com.example.secretsantaapp.user.UserDTO;
import com.example.secretsantaapp.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;


@RequiredArgsConstructor
@RequestMapping("/api/v1/potentialgifts")
@RestController
public class PotentialGiftController {

    private final PotentialGiftService potentialGiftService;
    private final UserService userService;

    @PostMapping("/{email}")
    public ResponseEntity<PotentialGiftDTO> addPotentialGiftForUser(@RequestBody PotentialGiftDTO potentialGiftDTO, @PathVariable String email) {
        if (isEmpty(email)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTO = userService.getUserByEmail(email);
        if (!userService.isUserEmailVerified(userDTO.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        potentialGiftService.addPotentialGift(potentialGiftDTO, email);
        return new ResponseEntity<>(potentialGiftDTO, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<PotentialGiftDTO>> getAllPotentialGiftsForUser(@PathVariable String email){
        if (isEmpty(email)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTO = userService.getUserByEmail(email);
        if (!userService.isUserEmailVerified(userDTO.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(potentialGiftService.getAllPotentialGiftsForUser(email), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePotentialGift(@PathVariable String id){
        potentialGiftService.deletePotentialGiftById(id);
        return null;
    }
}
