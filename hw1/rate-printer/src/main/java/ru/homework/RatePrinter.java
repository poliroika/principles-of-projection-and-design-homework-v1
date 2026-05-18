package ru.homework;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RatePrinter {
    private final RestClient restClient;

    public RatePrinter(RestClient restClient) {
        this.restClient = restClient;
    }

    @Scheduled(fixedRate = 5000)
    public void printRate() {
        try {
            RateResponse response = restClient.get()
                    .uri("http://localhost:8080/api/rate")
                    .retrieve()
                    .body(RateResponse.class);

            System.out.println(response.pair() + " = " + response.rate());
        } catch (Exception e) {
            System.out.println("Provider is not available: " + e.getMessage());
        }
    }
}
