package com.mindhub.Homebanking.Repositories;

import com.mindhub.Homebanking.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
