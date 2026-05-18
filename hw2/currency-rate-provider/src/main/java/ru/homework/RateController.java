package ru.homework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class RateController {
    private static final double BASE_USD_RUB_RATE = 92.0;

    private final String instanceUrl;

    public RateController(@Value("${server.port}") int serverPort) {
        this.instanceUrl = "http://localhost:" + serverPort;
    }

    @GetMapping("/api/rate")
    public RateResponse getRate() {
        double randomDelta = ThreadLocalRandom.current().nextDouble(-1.5, 1.5);
        double currentRate = round(BASE_USD_RUB_RATE + randomDelta);

        return new RateResponse("USDRUB", currentRate, instanceUrl);
    }

    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
