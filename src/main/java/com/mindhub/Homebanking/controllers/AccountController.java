package com.mindhub.Homebanking.controllers;


import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.repositories.AccountRepository;
import com.mindhub.Homebanking.dtos.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/")
    public ResponseEntity<List<AccountDTO>> getAllAccounts(){
        return new ResponseEntity <> (accountRepository.findAll().stream().map(AccountDTO::new).collect(toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getOneAccountById(@PathVariable Long id){
        Account account = accountRepository.findById(id).orElse(null);

        if(account  == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

        return  new ResponseEntity <> ( new AccountDTO(account), HttpStatus.OK);
    }
}
