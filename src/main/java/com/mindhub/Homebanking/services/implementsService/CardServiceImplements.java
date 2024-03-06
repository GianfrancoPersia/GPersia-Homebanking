package com.mindhub.Homebanking.services.implementsService;

import com.mindhub.Homebanking.models.Card;
import com.mindhub.Homebanking.repositories.CardRepository;
import com.mindhub.Homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplements implements CardService {

    @Autowired
    private CardRepository cardRepository;
    @Override
    public Card getCardByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
}
