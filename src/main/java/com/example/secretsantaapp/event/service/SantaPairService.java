package com.example.secretsantaapp.event.service;

import static com.example.secretsantaapp.util.CommonUtil.generateHashFromString;

import com.example.secretsantaapp.common.exception.GenericErrorException;
import com.example.secretsantaapp.event.model.SantaPair;
import com.example.secretsantaapp.event.repository.SantaPairRepository;
import com.example.secretsantaapp.user.User;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SantaPairService {
  private final SantaPairRepository santaPairRepository;

  @Autowired
  public SantaPairService(SantaPairRepository santaPairRepository) {
    this.santaPairRepository = santaPairRepository;
  }

  public SantaPair createSantaPair(User user, User secretSantaFor) {
    SantaPair santaPair = new SantaPair(user, secretSantaFor);
    try {
      String hash = generateHashFromString(user.getUsername() + secretSantaFor.getUsername());
      santaPair.setPairHash(hash);
      santaPairRepository.save(santaPair);

      return santaPair;
    } catch (NoSuchAlgorithmException ex) {
      throw new GenericErrorException(ex);
    }
  }
}
