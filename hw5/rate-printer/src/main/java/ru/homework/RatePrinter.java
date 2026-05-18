package ru.homework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RatePrinter {
    private final RateClient rateClient;
    private final String providerUrl;

    public RatePrinter(
            RateClient rateClient,
            @Value("${rate-provider.url}") String providerUrl
    ) {
        this.rateClient = rateClient;
        this.providerUrl = providerUrl;
    }

    @Scheduled(fixedRate = 5000)
    public void printRate() {
        try {
            RateResponse response = rateClient.getRate(providerUrl);
            System.out.println(response.pair() + " = " + response.rate());
        } catch (Exception e) {
            System.out.println("Provider is not available: " + e.getMessage());
        }
    }
}
