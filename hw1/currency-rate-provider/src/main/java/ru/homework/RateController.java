package ru.homework;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class RateController {
    private static final double BASE_USD_RUB_RATE = 92.0;

    @GetMapping("/api/rate")
    public RateResponse getRate() {
        double randomDelta = ThreadLocalRandom.current().nextDouble(-1.5, 1.5);
        double currentRate = round(BASE_USD_RUB_RATE + randomDelta);

        return new RateResponse("USDRUB", currentRate);
    }

    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
