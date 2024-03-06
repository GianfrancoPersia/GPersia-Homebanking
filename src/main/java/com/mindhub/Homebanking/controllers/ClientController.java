package com.mindhub.Homebanking.controllers;

import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.repositories.ClientRepository;
import com.mindhub.Homebanking.dtos.ClientDTO;
import com.mindhub.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;


    //traigo todos los clientes
    @GetMapping("/")
    public ResponseEntity <?> getAllClients(){

        return new ResponseEntity<>(clientService.getAllClientsDTO(),HttpStatus.OK);
    }

    //es una ruta variable para traer un cliente en especifico, si no existe devuelve null
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneClientById(@PathVariable Long id){
        Client client = clientService.getClientByID(id);
        if(client == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        return new ResponseEntity<>( client,HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getClient(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByEmail(userMail);

        return  ResponseEntity.ok(new ClientDTO(client));
    }

}
