package com.example.secretsantaapp.event.generator;

import com.example.secretsantaapp.event.dto.SantaPairDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PairGeneratorService {


    public List<SantaPairDTO> generatePairs(Collection<String> emails){
        List<String> receivers = emails.stream().toList();
        List<String> santas = emails.stream().toList();

        List<SantaPairDTO> pairs = Collections.emptyList();
        for (String santa: santas
             ) {
            var targets = receivers.stream()
                    .filter(name -> !name.equals(santa))
                    .collect(Collectors.toList());
            Collections.shuffle(targets);
            var target = targets.stream()
                    .findFirst().
                    get();
            pairs.add(new SantaPairDTO(santa, target));
            receivers.remove(target);
        }
        return pairs;
    }
}
