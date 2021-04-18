package com.zarwlad.newsaggregator;

import com.zarwlad.newsaggregator.tgclient.Receiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsaggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsaggregatorApplication.class, args);
    }

}
