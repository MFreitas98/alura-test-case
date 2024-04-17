package br.com.alura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TestCaseJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCaseJavaApplication.class, args);
	}

}
