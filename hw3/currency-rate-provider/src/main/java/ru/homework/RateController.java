package ru.homework;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class RateController {
    private static final double BASE_RATE = 92.0;

    @GetMapping("/api/rate")
    public RateResponse getRate() {
        double delta = ThreadLocalRandom.current().nextDouble(-1.5, 1.5);
        double rate = BigDecimal.valueOf(BASE_RATE + delta)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return new RateResponse("USDRUB", rate);
    }
}
