package pl.sparkbit.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.sparkbit.model.LatLng;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Created by chanter on 21.12.2016.
 */
@Service
public class ReactiveLocationService {

    private static final Logger LOG = LoggerFactory.getLogger(ReactiveLocationService.class);

    void storeCoordinates(Flux<LatLng> coordinates) {
        Flux.interval(Duration.ofSeconds(3))
                .zipWith(coordinates, (no, latLng) -> {
                    LOG.info("Stored {}", latLng);
                    return latLng;
                }).subscribe();
    }
}
