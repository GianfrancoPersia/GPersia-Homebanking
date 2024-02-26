package com.mindhub.Homebanking.controllers;

import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.repositories.ClientRepository;
import com.mindhub.Homebanking.dtos.ClientDTO;
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
    private ClientRepository clientRepository;


    //traigo todos los clientes
    @GetMapping("/")
    public ResponseEntity <?> getAllClients(){
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(clients);
    }

    //es una ruta variable para traer un cliente en especifico, si no existe devuelve null
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneClientById(@PathVariable Long id){
        Client client = clientRepository.findById(id).orElse(null);

        if(client == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        return  ResponseEntity.ok().body(client);
    }



}
