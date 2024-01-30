package com.mindhub.Homebanking;

import com.mindhub.Homebanking.Models.Client;
import com.mindhub.Homebanking.Repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {SpringApplication.run(HomebankingApplication.class, args);}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository){
		return args -> {
			Client client = new Client("Gianfranco","Persia", "gianipersia12@hotmail.com");
			Client client2 = new Client("Juan","Gomez", "juangomez@hotmail.com");
			Client client3 = new Client("Pedro","Rodriguez", "pedro_rodriguez@hotmail.com");
			Client client4 = new Client("Lucas","Sanchez", "lucassanchez01@gmail.com");

			clientRepository.save(client);
			clientRepository.save(client2);
			clientRepository.save(client3);
			clientRepository.save(client4);

		};
	}
}
