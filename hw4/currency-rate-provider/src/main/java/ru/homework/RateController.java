package ru.homework;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class RateController {
    private static final Logger log = LoggerFactory.getLogger(RateController.class);
    private static final double BASE_RATE = 92.0;

    private final MeterRegistry meterRegistry;

    public RateController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/api/rate")
    public RateResponse getRate(
            @RequestHeader(value = "X-Client-Name", defaultValue = "unknown") String client,
            HttpServletRequest request
    ) {
        log.info("request method={} path={} client={}", request.getMethod(), request.getRequestURI(), client);

        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            double delta = ThreadLocalRandom.current().nextDouble(-1.5, 1.5);
            double rate = BigDecimal.valueOf(BASE_RATE + delta)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            RateResponse response = new RateResponse("USDRUB", rate);

            Counter.builder("rate.provider.requests")
                    .description("Currency rate requests")
                    .tag("client", client)
                    .register(meterRegistry)
                    .increment();

            log.info("response client={} pair={} rate={}", client, response.pair(), response.rate());
            return response;
        } catch (RuntimeException e) {
            Counter.builder("rate.provider.errors")
                    .description("Provider 500 errors")
                    .tag("exception", e.getClass().getSimpleName())
                    .register(meterRegistry)
                    .increment();

            log.error("provider error", e);
            throw e;
        } finally {
            sample.stop(Timer.builder("rate.provider.request")
                    .description("Provider request duration")
                    .publishPercentileHistogram()
                    .publishPercentiles(0.5, 0.95, 0.99)
                    .register(meterRegistry));
        }
    }
}
