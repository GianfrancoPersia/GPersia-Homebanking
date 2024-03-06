package com.mindhub.Homebanking.repositories;

import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNumber (String number);

    Boolean existsByNumberAndClient(String number, Client client);


}
