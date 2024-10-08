package com.thi.realestateplatformsprojectbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@EnableJpaRepositories
public class RealEstatePlatformsProjectBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealEstatePlatformsProjectBeApplication.class, args);
	}

}
