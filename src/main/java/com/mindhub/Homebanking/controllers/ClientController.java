package com.mindhub.Homebanking.controllers;

import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.repositories.ClientRepository;
import com.mindhub.Homebanking.dtos.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;


    //traigo todos los clientes
    @GetMapping("/")
    public ResponseEntity <List<ClientDTO>> getAllClients(){
        return new ResponseEntity <> (clientRepository.findAll().stream().map(ClientDTO::new).collect(toList()), HttpStatus.OK);
    }

    //es una ruta variable para traer un cliente en especifico, si no existe devuelve null
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getOneClientById(@PathVariable Long id){
        Client client = clientRepository.findById(id).orElse(null);

        if(client == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

        return  new ResponseEntity <> ( new ClientDTO(client), HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String getClients(){
        return "Hellow Clients";
    }
}
