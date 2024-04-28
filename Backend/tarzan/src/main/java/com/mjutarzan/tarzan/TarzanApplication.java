package com.mjutarzan.tarzan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TarzanApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarzanApplication.class, args);
	}

}
