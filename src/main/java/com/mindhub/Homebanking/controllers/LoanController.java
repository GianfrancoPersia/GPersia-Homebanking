package com.mindhub.Homebanking.controllers;

import com.mindhub.Homebanking.dtos.LoanDTO;
import com.mindhub.Homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    LoanRepository loanRepository;

    @GetMapping("/")
    public ResponseEntity<List<LoanDTO>> getAllLoans(){
        return new ResponseEntity <> (loanRepository.findAll().stream().map(LoanDTO::new).collect(toList()), HttpStatus.OK);
    }
}
