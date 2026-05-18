package ru.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RateClient {
    private static final Logger log = LoggerFactory.getLogger(RateClient.class);
    private static final String CLIENT_NAME = "rate-printer";

    private final RestClient restClient;

    public RateClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public RateResponse getRate(String providerUrl) {
        String url = providerUrl + "/api/rate";

        log.info("request method=GET url={}", url);

        RateResponse response = restClient.get()
                .uri(url)
                .header("X-Client-Name", CLIENT_NAME)
                .retrieve()
                .body(RateResponse.class);

        log.info("response pair={} rate={}", response.pair(), response.rate());
        return response;
    }
}
