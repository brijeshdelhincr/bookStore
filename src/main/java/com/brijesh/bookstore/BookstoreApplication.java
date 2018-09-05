package com.brijesh.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.brijesh")
public class BookstoreApplication {

	public static void main(final String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
}
