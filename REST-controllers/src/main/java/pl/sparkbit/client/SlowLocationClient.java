package pl.sparkbit.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import pl.sparkbit.model.LatLng;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Random;

/**
 * Created by chanter on 14.12.2016.
 */
public class SlowLocationClient {

    private static final Logger LOG = LoggerFactory.getLogger(SlowLocationClient.class);

    public static void main(String[] argv) {
        ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
        ClientRequest<Flux<LatLng>> request = ClientRequest.POST("http://localhost:8080/location")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromPublisher(getPayload(), LatLng.class));
        LOG.info("Sending request");
        WebClient.create(httpConnector)
                .exchange(request)
                .doOnNext(cr -> LOG.info("Received {}", cr.statusCode())).block();
        LOG.info("Received response");
    }

    private static Flux<LatLng> getPayload() {
        Random random = new Random();
        return Flux.just(new LatLng(random.nextDouble(), random.nextDouble()))
                .zipWith(Flux.interval(Duration.ofSeconds(10)))
                .map(Tuple2::getT1);
    }
}
