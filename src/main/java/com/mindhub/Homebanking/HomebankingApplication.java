package com.mindhub.Homebanking;

import com.mindhub.Homebanking.Models.Account;
import com.mindhub.Homebanking.Models.Client;
import com.mindhub.Homebanking.Models.Transaction;
import com.mindhub.Homebanking.Models.TransactionType;
import com.mindhub.Homebanking.Repositories.AccountRepository;
import com.mindhub.Homebanking.Repositories.ClientRepository;
import com.mindhub.Homebanking.Repositories.TransactionRepository;
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
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return args -> {
			Client client_1 = new Client("Melba","Morel", "melbamorel@hotmail.com");
			Client client_2 = new Client("Gianfranco","Persia","gianfrancopersia@hotmail.com");

			Account account_1 = new Account("VIN001",LocalDate.now(),5000);
			Account account_2 = new Account("VIN002", LocalDate.now().plusDays(1),7500);

			Account account_3 = new Account("VIN003",LocalDate.now(),6000);
			Account account_4 = new Account("VIN004", LocalDate.now().plusDays(2),8000);

			//genero distintas transacciones
			Transaction transaction_1 = new Transaction(TransactionType.DEBIT,"Boleta luz",LocalDateTime.now(),200);
			Transaction transaction_2 = new Transaction(TransactionType.CREDIT,"Venta mercadolibre",LocalDateTime.now(),350);
			Transaction transaction_3 = new Transaction(TransactionType.DEBIT,"Corte de pelo",LocalDateTime.now(),50);
			Transaction transaction_4 = new Transaction(TransactionType.CREDIT,"Venta moto",LocalDateTime.now(),1500);
			Transaction transaction_5 = new Transaction(TransactionType.DEBIT,"Boleta agua",LocalDateTime.now(),185);
			Transaction transaction_6 = new Transaction(TransactionType.CREDIT,"Sueldo",LocalDateTime.now(),4500);


			//añado a cada cliente 2 cuentas
			client_1.addAccount(account_1);
			client_1.addAccount(account_2);

			client_2.addAccount(account_3);
			client_2.addAccount(account_4);

			//añado a cada cuenta transacciones
			account_1.addTransaction(transaction_1);
			account_1.addTransaction(transaction_2);

			account_2.addTransaction(transaction_3);
			account_2.addTransaction(transaction_4);

			account_3.addTransaction(transaction_5);
			account_4.addTransaction(transaction_6);


			clientRepository.save(client_1);
			accountRepository.save(account_1);
			accountRepository.save(account_2);


			clientRepository.save(client_2);
			accountRepository.save(account_3);
			accountRepository.save(account_4);

			//guardo todas las transacciones
			transactionRepository.save(transaction_1);
			transactionRepository.save(transaction_2);
			transactionRepository.save(transaction_3);
			transactionRepository.save(transaction_4);
			transactionRepository.save(transaction_5);
			transactionRepository.save(transaction_6);


		};
	}
}
