package com.tracker.corona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoronaCaseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronaCaseTrackerApplication.class, args);
	}

}
