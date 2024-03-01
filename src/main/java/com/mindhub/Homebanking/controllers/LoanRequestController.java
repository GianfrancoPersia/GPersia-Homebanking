package com.mindhub.Homebanking.controllers;

import com.mindhub.Homebanking.dtos.LoanApplicationDTO;
import com.mindhub.Homebanking.models.*;
import com.mindhub.Homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/clients/current")
public class LoanRequestController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<?>requestLoan(@RequestBody LoanApplicationDTO loanApplicationDTO){

        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        Account account = accountRepository.findByNumber(loanApplicationDTO.number());

        if(loanApplicationDTO.amount() <=0){
            return new ResponseEntity<>("You must enter an amount", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.payments() <= 0){
            return new ResponseEntity<>("You must enter a payment selection", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanRepository.findById(loanApplicationDTO.id()).orElse(null);

        if(loan == null){
            return new ResponseEntity<>("The selected loan type was not found", HttpStatus.FORBIDDEN);
        }

        if(loan.getMaxAmount() < loanApplicationDTO.amount()){
            return new ResponseEntity<>("The amount requested must be less than " + loan.getMaxAmount(), HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplicationDTO.payments())){
            return new ResponseEntity<>("The selected quotas must be: " + loan.getPayments(), HttpStatus.FORBIDDEN);
        }

        if (account == null){
            return new ResponseEntity<>("Target account does not exist",HttpStatus.FORBIDDEN);
        }

        Boolean accountExist = accountRepository.existsByNumberAndClient(loanApplicationDTO.number(), client);

        if(!accountExist){
            return new ResponseEntity<>("The destination account is not valid", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan((loanApplicationDTO.amount()*1.2),loanApplicationDTO.payments(), client, loan);

        Transaction transaction = new Transaction(TransactionType.CREDIT,loan.getName(), LocalDateTime.now(),loanApplicationDTO.amount());

        account.setBalance(account.getBalance() + loanApplicationDTO.amount());

        account.addTransaction(transaction);

        clientRepository.save(client);
        accountRepository.save(account);
        transactionRepository.save(transaction);
        clientLoanRepository.save(clientLoan);

        return new ResponseEntity<>("Total loan payable: "+ clientLoan.getAmount() + " at " + clientLoan.getPayments() + " payments " + "from: " + clientLoan.getAmount() / clientLoan.getPayments(), HttpStatus.CREATED);
    }
}
