package com.mindhub.Homebanking;

import com.mindhub.Homebanking.models.*;
import com.mindhub.Homebanking.repositories.*;
import com.mindhub.Homebanking.utils.MathRandom;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import  org.junit.jupiter.api.Test;
import com.mindhub.Homebanking.utils.CardUtils;
import com.mindhub.Homebanking.utils.MathRandom;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    TransactionRepository transactionRepository;


    @Test
    void cardNumberIsCreated(){
        MathRandom mathRandom = new MathRandom();
        String cardNumber = CardUtils.CardNumber(mathRandom);

        assertThat(cardNumber,is(not(emptyOrNullString())));
        assertThat(cardNumber.length(), equalTo(19));
    }

    @Test
    void cardCvvIsCreated(){
        MathRandom mathRandom = new MathRandom();
        String cardCvv = CardUtils.CardCvv(mathRandom);

        assertThat(cardCvv,is(not(emptyOrNullString())));
        assertThat(cardCvv.length(), equalTo(3));
    }

    @Test
    public void existAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }

    @Test
    public void existClients(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }

    @Test
    public void existClientLoans(){
        List<ClientLoan> clientLoans = clientLoanRepository.findAll();
        assertThat(clientLoans,is(not(empty())));
    }

    @Test
    public void existCards(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }

    @Test
    public void existTransactions(){
        List<Transaction> trasactions = transactionRepository.findAll();
        assertThat(trasactions,is(not(empty())));
    }

    @Test
    public void existLoans(){

        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Test
    public void existClientByName(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("name", is("Melba"))));
    }

    @Test
    public void existsAccountByNumberAndClient(){
        Client client = clientRepository.findByEmail("melbamorel@hotmail.com");
        Boolean account = accountRepository.existsByNumberAndClient("VIN001",client);
        assertThat(account,is(true));
    }

    @Test
    public void existCardByNumber(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("number", is("3455-7610-4090-0011"))));
    }

    @Test
    public void existTransactionByAmount(){
        List<Transaction> trasactions = transactionRepository.findAll();
        assertThat(trasactions, hasItem(hasProperty("amount", is(200.0))));
    }

}
