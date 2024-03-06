package com.mindhub.Homebanking.services;



import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Client;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();
    Account getAccountByID(Long id);
    Account getAccountByNumber (String number);
    Boolean existsByNumberAndClient(String number, Client client);
    void saveAccount(Account account);
}
