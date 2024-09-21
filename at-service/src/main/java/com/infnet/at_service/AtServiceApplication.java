package com.infnet.at_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class AtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtServiceApplication.class, args);
	}

}
