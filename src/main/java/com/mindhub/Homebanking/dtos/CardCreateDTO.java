package com.mindhub.Homebanking.dtos;


import com.mindhub.Homebanking.models.CardColor;
import com.mindhub.Homebanking.models.CardType;

public record CardCreateDTO(CardType type, CardColor color) {


}
