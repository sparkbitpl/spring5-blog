package pl.sparkbit.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;


/**
 * Created by chanter on 12.12.2016.
 */
@RestController
public class OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    private final static String[] ORDER_STAGES = {"RECEIVED", "ASSEMBLING_PIZZA", "BACKING", "QA", "DELIVERY"};

    /**
     * Server sent event  implementation
     */
    @GetMapping(path = "order", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> logOrder() {
        LOG.info("SSE connection established");
        return Flux.interval(Duration.ofSeconds(3L))
                .zipWith(Flux.fromArray(ORDER_STAGES),
                        (i, status) -> status);
    }


}
