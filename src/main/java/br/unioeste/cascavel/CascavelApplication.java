package br.unioeste.cascavel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class CascavelApplication {

	public static void main(String[] args) {
		SpringApplication.run(CascavelApplication.class, args);
	}

}
