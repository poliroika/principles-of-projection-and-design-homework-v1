package ru.homework;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RatePrinter {
    private final ProviderRegistry providerRegistry;
    private final RestClient restClient;
    private final AtomicInteger requestCounter = new AtomicInteger();

    public RatePrinter(ProviderRegistry providerRegistry, RestClient restClient) {
        this.providerRegistry = providerRegistry;
        this.restClient = restClient;
    }

    @Scheduled(fixedRate = 5000)
    public void printRate() {
        try {
            List<String> providerUrls = providerRegistry.getProviderUrls();

            if (providerUrls.isEmpty()) {
                System.out.println("No currency-rate-provider instances found");
                return;
            }

            String providerUrl = selectProvider(providerUrls);
            RateResponse response = restClient.get()
                    .uri(providerUrl + "/api/rate")
                    .retrieve()
                    .body(RateResponse.class);

            System.out.println(
                    "instances=" + providerUrls.size()
                            + ", used=" + providerUrl
                            + ", " + response.pair()
                            + "=" + response.rate()
            );
        } catch (Exception e) {
            System.out.println("Failed to print rate: " + e.getMessage());
        }
    }

    private String selectProvider(List<String> providerUrls) {
        int index = Math.floorMod(requestCounter.getAndIncrement(), providerUrls.size());
        return providerUrls.get(index);
    }
}
