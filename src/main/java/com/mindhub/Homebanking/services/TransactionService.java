package com.mindhub.Homebanking.services;

import com.mindhub.Homebanking.models.Transaction;

public interface TransactionService {
    void saveTransaction(Transaction transaction);
}
