package com.mindhub.Homebanking.controllers;

import com.mindhub.Homebanking.dtos.AccountDTO;
import com.mindhub.Homebanking.dtos.CardCreateDTO;
import com.mindhub.Homebanking.dtos.CardDTO;
import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Card;
import com.mindhub.Homebanking.models.CardType;
import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.repositories.AccountRepository;
import com.mindhub.Homebanking.repositories.CardRepository;
import com.mindhub.Homebanking.repositories.ClientRepository;
import com.mindhub.Homebanking.utils.MathRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/clients/current")
public class CardController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MathRandom mathRandom;

    @PostMapping("/cards")
    public ResponseEntity<?> createCard(@RequestBody CardCreateDTO cardCreateDTO){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        List<Card> cards = client.getCards().stream().toList();

        List <Boolean> cardsTypeColor = cards.stream().map(card -> card.getType() == cardCreateDTO.type() && card.getColor() == cardCreateDTO.color()).toList();

        if(cardsTypeColor.contains(true)){
            return new ResponseEntity<>("You already have one card with type " + cardCreateDTO.type() + " and color " + cardCreateDTO.color(), HttpStatus.FORBIDDEN);
        }
        String number = String.format("%04d", mathRandom.getRandomNumber(0, 1001)) + "-" + String.format("%04d", mathRandom.getRandomNumber(0, 1001)) + "-" + String.format("%04d", mathRandom.getRandomNumber(0, 1001)) + "-" + String.format("%04d", mathRandom.getRandomNumber(0, 1001));

        while (cardRepository.findByNumber(number) != null){
            number = String.format("%04d", mathRandom.getRandomNumber(0, 1001)) + "-" + String.format("%04d", mathRandom.getRandomNumber(0, 1001)) + "-" + String.format("%04d", mathRandom.getRandomNumber(0, 1001)) + "-" + String.format("%04d", mathRandom.getRandomNumber(0, 1001));
        }

        String cvv = String.format("%03d", mathRandom.getRandomNumber(0, 1000));


        Card card = new Card(cardCreateDTO.type(),cardCreateDTO.color(),number,cvv, LocalDate.now());

        client.addCard(card);

        clientRepository.save(client);
        cardRepository.save(card);

        return new ResponseEntity<>("New card generated", HttpStatus.CREATED);
    }

    @GetMapping("/cards")
    public ResponseEntity<?> getCard(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);
        List<Card> cards = client.getCards().stream().toList();

        return ResponseEntity.ok(cards.stream().map(CardDTO::new).toList());
    }
}
