package br.unioeste.cascavel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.unioeste.cascavel.domain.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CascavelApplication {

	public static void main(String[] args) {
		SpringApplication.run(CascavelApplication.class, args);
	}

	// @Bean
	// CommandLineRunner init(StorageService storageService) {
	// 	return (args) -> {
	// 		storageService.deleteAll();
	// 		storageService.init();
	// 	};
	// }

}
