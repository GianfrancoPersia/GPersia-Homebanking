package com.mindhub.Homebanking.services;

import com.mindhub.Homebanking.dtos.ClientDTO;
import com.mindhub.Homebanking.models.Client;

import java.util.List;


public interface ClientService {

    List<Client> getAllClients();

    List<ClientDTO> getAllClientsDTO();

    Client getClientByID(Long id);

    Client getClientByEmail(String email);
}
