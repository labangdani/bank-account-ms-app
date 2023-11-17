package com.example.account_service;

import com.example.account_service.clients.CustomerRestClient;
import com.example.account_service.entities.BankAccount;
import com.example.account_service.enums.AccountType;
import com.example.account_service.repository.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository, CustomerRestClient customerRestClient){
		return args -> {
			customerRestClient.allCustomers().forEach(c->{
				List<BankAccount> accountList=List.of(
						BankAccount.builder()
								.accountId(UUID.randomUUID().toString())
								.currency("MAD")
								.balance(Math.random()*80000)
								.createAt(LocalDate.now())
								.type(AccountType.CURRENT_ACCOUNT)
								.customerId(c.getId())
								.build(),
						BankAccount.builder()
								.accountId(UUID.randomUUID().toString())
								.currency("MAD")
								.balance(Math.random()*65432)
								.createAt(LocalDate.now())
								.type(AccountType.SAVING_ACCOUNT)
								.customerId(c.getId())
								.build()
				);
				bankAccountRepository.saveAll(accountList);
			});

		};
	}


}
