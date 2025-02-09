package com.br.pruma;

import com.br.pruma.servicos.MySQLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrumaApplication {
	@Autowired
	public MySQLConnection mySQLConnection;


	public static void main(String[] args) {
		SpringApplication.run(PrumaApplication.class, args);


	}

}
