package ru.homework;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RateClient {
    private final RestClient restClient;

    public RateClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public RateResponse getRate(String providerUrl) {
        return restClient.get()
                .uri(providerUrl + "/api/rate")
                .retrieve()
                .body(RateResponse.class);
    }
}
