package com.mindhub.Homebanking.controllers;

import com.mindhub.Homebanking.dtos.ClientDTO;
import com.mindhub.Homebanking.dtos.LoginDTO;
import com.mindhub.Homebanking.dtos.RegisterDTO;
import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.repositories.AccountRepository;
import com.mindhub.Homebanking.repositories.ClientRepository;
import com.mindhub.Homebanking.services.JwtUtilService;
import com.mindhub.Homebanking.utils.MathRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MathRandom mathRandom;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/login")
    public  ResponseEntity<?> login (@RequestBody LoginDTO loginDTO){
        try {
            Client client = clientRepository.findByEmail(loginDTO.email());

            if(client == null){
                return ResponseEntity.status(400).body("Email or password incorrect");
            }

            if(!passwordEncoder.matches(loginDTO.password(), client.getPassword())){
                return ResponseEntity.status(400).body("Email or password incorrect");
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
            final String jwt = jwtUtilService.generateToken(userDetails);
            return ResponseEntity.ok(jwt);
        }catch (Exception e){
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {

        try {
            if (registerDTO.firstName().isBlank() || registerDTO.lastName().isBlank() || registerDTO.password().isBlank() || registerDTO.email().isBlank()) {
                return ResponseEntity.status(400).body("Incomplete fields");
            }

            if (registerDTO.password().length() < 6) {
                return ResponseEntity.status(400).body("The password must contain at least 6 characters");
            }
            if (!registerDTO.email().contains("@")) {
                return ResponseEntity.status(400).body("The email must contain @");
            }
            // creo el cliente una vez pasado los filtros de validacion
            Client newClient = new Client(
                    registerDTO.firstName(),
                    registerDTO.lastName(),
                    registerDTO.email(),
                    passwordEncoder.encode(registerDTO.password()));

            String number = "VIN" + mathRandom.getRandomNumber(1,99999999);

            while (accountRepository.findByNumber(number) != null){
                number = "VIN" + mathRandom.getRandomNumber(1,99999999);
            }

            Account account = new Account(number, LocalDate.now(),0);
            newClient.addAccount(account);

            clientRepository.save(newClient);
            accountRepository.save(account);

            return new ResponseEntity<>("Created" , HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/test")
    public ResponseEntity<?>test(){
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        return  ResponseEntity.ok("Hello " + mail);
    }
}
