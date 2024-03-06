package com.mindhub.Homebanking.services;

import com.mindhub.Homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> getAllLoans();

    Loan getLoanById(Long id);
}
