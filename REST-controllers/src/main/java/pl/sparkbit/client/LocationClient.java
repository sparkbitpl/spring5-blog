package pl.sparkbit.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import pl.sparkbit.model.Address;
import pl.sparkbit.model.LatLng;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Created by chanter on 14.12.2016.
 */
public class LocationClient {

    private static final Logger LOG = LoggerFactory.getLogger(LocationClient.class);

    public static void main(String[] argv) {
        ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
        ClientRequest<Flux<LatLng>> request = ClientRequest.POST("http://localhost:8080/location/address")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromPublisher(getLatLngs(), LatLng.class));
        WebClient.create(httpConnector)
                .exchange(request)
                .flatMap(cr -> cr.bodyToFlux(Address.class))
                .doOnNext(a -> {
                    LOG.info("Received {}", a.getCity());
                }).collectList().block();
    }

    private static Flux<LatLng> getLatLngs() {
        return Flux.range(0, 3)
                .zipWith(Flux.interval(Duration.ofSeconds(1)))
                .map(x -> new LatLng(41.9102411,12.395572))
                .doOnNext(ll -> LOG.info("Produced: {}", ll));
    }
}
