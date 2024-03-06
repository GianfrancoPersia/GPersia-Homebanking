package com.mindhub.Homebanking.services.implementsService;

import com.mindhub.Homebanking.models.Loan;
import com.mindhub.Homebanking.repositories.LoanRepository;
import com.mindhub.Homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImplements implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

}
