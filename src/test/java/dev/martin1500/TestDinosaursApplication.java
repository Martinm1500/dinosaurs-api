package dev.martin1500;

import org.springframework.boot.SpringApplication;

public class TestDinosaursApplication {

	public static void main(String[] args) {
		SpringApplication.from(DinosaursApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
