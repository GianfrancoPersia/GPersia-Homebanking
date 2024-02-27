package com.mindhub.Homebanking.controllers;

import com.mindhub.Homebanking.dtos.AccountDTO;
import com.mindhub.Homebanking.dtos.CardCreateDTO;
import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.repositories.AccountRepository;
import com.mindhub.Homebanking.repositories.ClientRepository;
import com.mindhub.Homebanking.utils.MathRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/clients/current")
public class CreateAccountController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MathRandom mathRandom;
    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        List<Account> accounts = client.getAccounts().stream().toList();

        if (accounts.size() < 3){
            String number = "VIN" + mathRandom.getRandomNumber(1,99999999);

            while (accountRepository.findByNumber(number) != null){
                number = "VIN" + mathRandom.getRandomNumber(1,99999999);
            }

            Account account = new Account(number, LocalDate.now(),0);
            client.addAccount(account);
            clientRepository.save(client);
            accountRepository.save(account);

            return new ResponseEntity<>("Account created",HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("You cannot have more than 3 accounts", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAccount(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);
        List<Account> accounts = client.getAccounts().stream().toList();

        return ResponseEntity.ok(accounts.stream().map(AccountDTO::new).toList());
    }
}
