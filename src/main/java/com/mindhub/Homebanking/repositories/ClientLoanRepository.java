package com.mindhub.Homebanking.repositories;

import com.mindhub.Homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoanRepository extends JpaRepository<ClientLoan,Long> {
}
