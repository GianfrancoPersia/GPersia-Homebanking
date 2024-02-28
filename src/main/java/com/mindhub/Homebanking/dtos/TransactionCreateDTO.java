package com.mindhub.Homebanking.dtos;

import com.mindhub.Homebanking.models.TransactionType;

import java.time.LocalDate;

public record TransactionCreateDTO (double amount, String description, String numberCredit, String numberDebit) {

}
