 package com.example.macedonianshubb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MacedonianShubApplication {

    public static void main(String[] args) {
        SpringApplication.run(MacedonianShubApplication.class, args);

        // Start data fetching and writing to CSV
        DataFetcher.fetchAndWriteData();
    }
}