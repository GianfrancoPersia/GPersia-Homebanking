package com.mindhub.Homebanking.Controllers;

import com.mindhub.Homebanking.Models.Client;
import com.mindhub.Homebanking.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    //traigo todos los clientes
    @GetMapping
    public List<Client> getAllClients(){

        return clientRepository.findAll();
    }

    //es una ruta variable para traer un cliente en especifico, si no existe devuelve null
    @GetMapping("/{id}")
    public Client getOneClientById(@PathVariable Long id){
        return  clientRepository.findById(id).orElse(null);
    }

    @GetMapping("/hello")
    public String getClients(){
        return "Hellow Clients";
    }
}
