package com.evil.inc.account.command;

import com.evil.inc.account.command.infrastructure.CommandHandler;
import com.evil.inc.cqrs.core.commands.Command;
import com.evil.inc.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class TechbankAccountCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechbankAccountCommandApplication.class, args);
	}

}
