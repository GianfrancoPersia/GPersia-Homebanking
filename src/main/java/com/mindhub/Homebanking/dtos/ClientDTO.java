package com.mindhub.Homebanking.dtos;


import com.mindhub.Homebanking.Models.Client;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;



public class ClientDTO {
    private Long id;
    private String name, lastName, email;

    private List<AccountDTO> accounts = new ArrayList<>();

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(toList());
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
}
