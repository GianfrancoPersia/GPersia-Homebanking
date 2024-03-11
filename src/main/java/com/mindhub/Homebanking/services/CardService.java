package com.mindhub.Homebanking.services;

import com.mindhub.Homebanking.models.Card;
import com.mindhub.Homebanking.utils.MathRandom;

public interface CardService {
    Card getCardByNumber (String number);

    void saveCard(Card card);

    String getCVV(MathRandom mathRandom);

    String getCardNumber(MathRandom mathRandom);
}
