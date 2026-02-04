package com.gym.gymapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class GymappApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymappApplication.class, args);
	}

}
