package com.br.pruma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.br.pruma.core.domain")
public class PrumaApplication {


	public static void main(String[] args) {
		SpringApplication.run(PrumaApplication.class, args);
	}

}
