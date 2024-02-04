package com.mindhub.Homebanking;

import com.mindhub.Homebanking.Models.Account;
import com.mindhub.Homebanking.Models.Client;
import com.mindhub.Homebanking.Repositories.AccountRepository;
import com.mindhub.Homebanking.Repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {SpringApplication.run(HomebankingApplication.class, args);}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return args -> {
			Client client_1 = new Client("Melba","Morel", "melbamorel@hotmail.com");
			Client client_2 = new Client("Gianfranco","Persia","gianfrancopersia@hotmail.com");

			Account account_1 = new Account("VIN001",LocalDate.now(),5000);
			Account account_2 = new Account("VIN002", LocalDate.now().plusDays(1),7500);

			Account account_3 = new Account("VIN003",LocalDate.now(),6000);
			Account account_4 = new Account("VIN004", LocalDate.now().plusDays(2),8000);

			client_1.addAccount(account_1);
			client_1.addAccount(account_2);

			client_2.addAccount(account_3);
			client_2.addAccount(account_4);


			clientRepository.save(client_1);
			accountRepository.save(account_1);
			accountRepository.save(account_2);

			clientRepository.save(client_2);
			accountRepository.save(account_3);
			accountRepository.save(account_4);


			System.out.println(client_1);
		};
	}
}
