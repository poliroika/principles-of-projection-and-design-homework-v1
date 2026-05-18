package ru.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;

@EnableScheduling
@SpringBootApplication
public class RatePrinterApplication {
    private static final Logger log = LoggerFactory.getLogger(RatePrinterApplication.class);

    @Value("${app.version}")
    private String appVersion;

    public static void main(String[] args) {
        SpringApplication.run(RatePrinterApplication.class, args);
    }

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }

    @EventListener(ApplicationReadyEvent.class)
    void logVersion() {
        log.info("rate-printer version {}", appVersion);
    }
}
