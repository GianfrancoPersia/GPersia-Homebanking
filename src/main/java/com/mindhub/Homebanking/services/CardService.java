package com.mindhub.Homebanking.services;

import com.mindhub.Homebanking.models.Card;

public interface CardService {
    Card getCardByNumber (String number);

    void saveCard(Card card);
}
