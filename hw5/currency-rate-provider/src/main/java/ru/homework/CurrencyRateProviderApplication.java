package ru.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class CurrencyRateProviderApplication implements DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(CurrencyRateProviderApplication.class);

    @Value("${app.version}")
    private String appVersion;

    public static void main(String[] args) {
        SpringApplication.run(CurrencyRateProviderApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    void logVersion() {
        log.info("currency-rate-provider version {}", appVersion);
    }

    @Override
    public void destroy() {
        log.info("currency-rate-provider stopped");
    }
}
