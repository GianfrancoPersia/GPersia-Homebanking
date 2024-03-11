package com.mindhub.Homebanking.services.implementsService;

import com.mindhub.Homebanking.models.Card;
import com.mindhub.Homebanking.repositories.CardRepository;
import com.mindhub.Homebanking.services.CardService;
import com.mindhub.Homebanking.utils.MathRandom;
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

    @Override
    public String getCVV(MathRandom mathRandom) {
        String cvv = String.format("%03d", mathRandom.getRandomNumber(0, 1000));
        return cvv;
    }

    @Override
    public String getCardNumber(MathRandom mathRandom) {
        String number = String.format("%04d", mathRandom.getRandomNumber(0, 10000)) + "-" + String.format("%04d", mathRandom.getRandomNumber(0, 10000)) + "-" + String.format("%04d", mathRandom.getRandomNumber(0, 10000)) + "-" + String.format("%04d", mathRandom.getRandomNumber(0, 10000));
        return number;
    }
}
