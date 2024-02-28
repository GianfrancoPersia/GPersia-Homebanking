package com.mindhub.Homebanking.controllers;

import com.mindhub.Homebanking.dtos.TransactionCreateDTO;
import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.models.Transaction;
import com.mindhub.Homebanking.models.TransactionType;
import com.mindhub.Homebanking.repositories.AccountRepository;
import com.mindhub.Homebanking.repositories.ClientRepository;
import com.mindhub.Homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/clients/current")
public class TransactionController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<?> createTransactions(@RequestBody TransactionCreateDTO transactionCreateDTO){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        if (transactionCreateDTO.description().isBlank()){
            return new ResponseEntity<>("The description field is empty", HttpStatus.FORBIDDEN);
        }
        if (transactionCreateDTO.amount() <= 0 ){
            return  new ResponseEntity<>("You cannot send this amount", HttpStatus.FORBIDDEN);
        }
        if(transactionCreateDTO.numberCredit().isBlank()){
            return new ResponseEntity<>("You must enter a destination account",HttpStatus.FORBIDDEN);
        }

        Account accountCredit = accountRepository.findByNumber(transactionCreateDTO.numberCredit());

        if (accountCredit == null){
            return new ResponseEntity<>("Target account does not exist",HttpStatus.FORBIDDEN);
        }

        if (transactionCreateDTO.numberDebit().equals(transactionCreateDTO.numberCredit())){
            return new ResponseEntity<>("You cannot autotransfer money to your own account",HttpStatus.FORBIDDEN);
        }

        if(!client.getAccounts().stream().anyMatch(account -> account.getNumber().equals(transactionCreateDTO.numberDebit()))){
            return new ResponseEntity<>("The origin account is not valid", HttpStatus.FORBIDDEN);
        }

        Account accountDebit = accountRepository.findByNumber(transactionCreateDTO.numberDebit());

        if (accountDebit.getBalance() < transactionCreateDTO.amount()){
            return new ResponseEntity<>("You do not have enough money", HttpStatus.FORBIDDEN);
        }

        accountDebit.setBalance(accountDebit.getBalance() - transactionCreateDTO.amount());
        accountCredit.setBalance(accountCredit.getBalance() + transactionCreateDTO.amount());

        Transaction transactionDebit = new Transaction(TransactionType.DEBIT,transactionCreateDTO.description(), LocalDateTime.now(), - (transactionCreateDTO.amount()));
        Transaction transactionCredit = new Transaction(TransactionType.CREDIT,transactionCreateDTO.description(), LocalDateTime.now(), transactionCreateDTO.amount());
        transactionRepository.save(transactionDebit);
        transactionRepository.save(transactionCredit);

        accountDebit.addTransaction(transactionDebit);
        accountCredit.addTransaction(transactionCredit);
        accountRepository.save(accountDebit);
        accountRepository.save(accountCredit);

        clientRepository.save(client);

        return new ResponseEntity<>("Transaction sucessfully", HttpStatus.CREATED);
    }
}
