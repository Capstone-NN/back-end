package com.capstone.NANOOM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Config로 옮겨도 됨
public class NanoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(NanoomApplication.class, args);
	}

}
