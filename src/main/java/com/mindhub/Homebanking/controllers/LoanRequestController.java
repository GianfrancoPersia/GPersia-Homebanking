package com.mindhub.Homebanking.controllers;

import com.mindhub.Homebanking.dtos.LoanApplicationDTO;
import com.mindhub.Homebanking.models.*;
import com.mindhub.Homebanking.repositories.*;
import com.mindhub.Homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/clients/current")
public class LoanRequestController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientLoanService clientLoanService;

    //Es una anotacion de Spring que se utiliza para administrar procesos transaccionales
    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<?>requestLoan(@RequestBody LoanApplicationDTO loanApplicationDTO){

        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByEmail(userMail);

        Account account = accountService.getAccountByNumber(loanApplicationDTO.number());

        if(loanApplicationDTO.amount() <=0){
            return new ResponseEntity<>("You must enter an amount", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.payments() <= 0){
            return new ResponseEntity<>("You must enter a payment selection", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanService.getLoanById(loanApplicationDTO.id());

        if(loan == null){
            return new ResponseEntity<>("The selected loan type was not found", HttpStatus.FORBIDDEN);
        }

        if(loan.getMaxAmount() < loanApplicationDTO.amount()){
            return new ResponseEntity<>("The amount requested must be less than " + loan.getMaxAmount(), HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplicationDTO.payments())){
            return new ResponseEntity<>("The selected quotas must be: " + loan.getPayments(), HttpStatus.FORBIDDEN);
        }

        if (account == null){
            return new ResponseEntity<>("Target account does not exist",HttpStatus.FORBIDDEN);
        }

        Boolean accountExist = accountService.existsByNumberAndClient(loanApplicationDTO.number(), client);

        if(!accountExist){
            return new ResponseEntity<>("The destination account is not valid", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan((loanApplicationDTO.amount()*1.2),loanApplicationDTO.payments(), client, loan);

        Transaction transaction = new Transaction(TransactionType.CREDIT,loan.getName(), LocalDateTime.now(),loanApplicationDTO.amount());

        account.setBalance(account.getBalance() + loanApplicationDTO.amount());

        account.addTransaction(transaction);

        clientService.saveClient(client);
        accountService.saveAccount(account);
        transactionService.saveTransaction(transaction);
        clientLoanService.saveClientLoan(clientLoan);

        return new ResponseEntity<>("Total loan payable: "+ clientLoan.getAmount() + " at " + clientLoan.getPayments() + " payments " + "from: " + clientLoan.getAmount() / clientLoan.getPayments(), HttpStatus.CREATED);
    }
}
