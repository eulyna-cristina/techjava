package br.uniesp.si.techback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // IMPORTANTE

@SpringBootApplication
@EnableFeignClients // ADICIONE ESTA ANOTAÇÃO AQUI
public class TechbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechbackApplication.class, args);
	}
}