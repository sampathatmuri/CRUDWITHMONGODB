package com.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongo.repository.StudentRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = StudentRepository.class)
public class CrudwithmongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudwithmongodbApplication.class, args);
	}

}
