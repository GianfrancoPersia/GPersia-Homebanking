package com.mindhub.Homebanking.Repositories;

import com.mindhub.Homebanking.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
