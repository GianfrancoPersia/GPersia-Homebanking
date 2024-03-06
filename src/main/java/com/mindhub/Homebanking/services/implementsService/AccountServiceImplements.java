package com.mindhub.Homebanking.services.implementsService;

import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.repositories.AccountRepository;
import com.mindhub.Homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImplements implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountByID(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account getAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public Boolean existsByNumberAndClient(String number, Client client) {
        return accountRepository.existsByNumberAndClient(number,client);
    }


    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }


}
