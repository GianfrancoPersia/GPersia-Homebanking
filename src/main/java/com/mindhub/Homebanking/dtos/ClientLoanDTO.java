package com.mindhub.Homebanking.dtos;

import com.mindhub.Homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private Long id;

    private String name;

    private double amount;

    private int payments;


    private Long loanId;


    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.loanId = clientLoan.getLoan().getId();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public Long getLoanId() {
        return loanId;
    }
}
