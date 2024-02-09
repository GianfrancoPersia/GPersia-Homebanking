package com.mindhub.Homebanking.dtos;


import com.mindhub.Homebanking.models.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


public class ClientDTO {
    private Long id;
    private String name, lastName, email;

    private List<AccountDTO> accounts = new ArrayList<>();

    private Set<ClientLoanDTO> clientLoans;

    private Set<CardDTO> cards;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(toList());
        this.clientLoans = client.getClientLoans().stream().map(ClientLoanDTO::new).collect(toSet());
        this.cards = client.getCards().stream().map(CardDTO::new).collect(toSet());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getClientLoans() {
        return clientLoans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
