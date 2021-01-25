package com.antra.evaluation.reporting_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories
public class ReportingSystemPDFApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportingSystemPDFApplication.class, args);
    }

}
