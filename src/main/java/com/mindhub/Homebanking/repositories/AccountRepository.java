package com.mindhub.Homebanking.repositories;

import com.mindhub.Homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
