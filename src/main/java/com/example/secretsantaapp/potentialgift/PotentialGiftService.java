package com.example.secretsantaapp.potentialgift;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PotentialGiftService {
    private final PotentialGiftRepository potentialGiftRepository;

    public List<PotentialGiftDTO> getAllPotentialGiftsForUser(String email) {
        List<PotentialGift> potentialGifts = potentialGiftRepository.findPotentialGiftByEmail(email).orElse(Collections.EMPTY_LIST);
        return potentialGifts.stream()
                .map(gift -> new PotentialGiftDTO(gift.getName(), gift.getDescription()))
                .collect(Collectors.toList());
    }

    public Optional<PotentialGift> getPotentialGiftById(String id) {
        return potentialGiftRepository.findById(id);
    }

    public PotentialGift addPotentialGift(PotentialGiftDTO potentialGiftDTO, String email) {
        var potentialGift = new PotentialGift();
                potentialGift.setEmail(email);
                potentialGift.setName(potentialGiftDTO.getName());
                potentialGift.setDescription(potentialGiftDTO.getDescription());
                return potentialGiftRepository.save(potentialGift);
    }

    public void deletePotentialGiftById(String id) {
        potentialGiftRepository.deleteById(id);
    }
}
