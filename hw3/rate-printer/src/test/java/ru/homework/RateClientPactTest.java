package ru.homework;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.LambdaDsl;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(pactVersion = PactSpecVersion.V3)
class RateClientPactTest {
    @Pact(consumer = "rate-printer", provider = "currency-rate-provider")
    RequestResponsePact ratePact(PactDslWithProvider builder) {
        return builder
                .given("rate is available")
                .uponReceiving("request for currency rate")
                    .path("/api/rate")
                    .method("GET")
                .willRespondWith()
                    .status(200)
                    .headers(Map.of("Content-Type", "application/json"))
                    .body(LambdaDsl.newJsonBody(body -> {
                        body.stringType("pair", "USDRUB");
                        body.decimalType("rate", 92.25);
                    }).build())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "ratePact")
    void shouldReadRate(MockServer mockServer) {
        RateClient client = new RateClient(RestClient.create());

        RateResponse response = client.getRate(mockServer.getUrl());

        assertThat(response.pair()).isEqualTo("USDRUB");
        assertThat(response.rate()).isGreaterThan(0);
    }
}
