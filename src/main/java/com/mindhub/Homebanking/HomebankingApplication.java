package com.mindhub.Homebanking;

import com.mindhub.Homebanking.models.*;
import com.mindhub.Homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {SpringApplication.run(HomebankingApplication.class, args);}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, ClientLoanRepository clientLoanRepository, LoanRepository loanRepository){
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


			Loan mortgage = new Loan("Mortgage",500000, List.of(12,24,36,48,60));
			Loan personal = new Loan("Personal",100000,List.of(6,12,24));
			Loan automotive = new Loan("Automotive",300000,List.of(6,12,24,36));


			//Melba
			ClientLoan clientLoan_1 = new ClientLoan(400000,60,client_1,mortgage);
			client_1.addLoanToMyClient(clientLoan_1);
			mortgage.addLoanToMyClient(clientLoan_1);

			ClientLoan clientLoan_2 = new ClientLoan(50000,12,client_1,personal);
			client_1.addLoanToMyClient(clientLoan_2);
			personal.addLoanToMyClient(clientLoan_2);

			//objeto 2
			ClientLoan clientLoan_3 = new ClientLoan(100000,24,client_2,personal);
			client_2.addLoanToMyClient(clientLoan_3);
			personal.addLoanToMyClient(clientLoan_3);

			ClientLoan clientLoan_4 = new ClientLoan(200000,36,client_2,automotive);
			client_2.addLoanToMyClient(clientLoan_4);
			automotive.addLoanToMyClient(clientLoan_4);

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


			loanRepository.save(mortgage);
			loanRepository.save(automotive);
			loanRepository.save(personal);


			clientLoanRepository.save(clientLoan_1);
			clientLoanRepository.save(clientLoan_2);
			clientLoanRepository.save(clientLoan_3);
			clientLoanRepository.save(clientLoan_4);



			System.out.println(client_1);


		};
	}
}
