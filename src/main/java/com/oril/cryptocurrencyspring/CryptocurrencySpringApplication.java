package com.oril.cryptocurrencyspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CryptocurrencySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptocurrencySpringApplication.class, args);
	}

}
