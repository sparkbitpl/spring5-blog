package pl.sparkbit.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Created by chanter on 14.12.2016.
 */
public class OrderClient {

    private static final Logger LOG = LoggerFactory.getLogger(OrderClient.class);

    public static void main(String[] argv) {
        ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
        ClientRequest<Void> request = ClientRequest.GET("http://localhost:8080/order")
                .accept(MediaType.TEXT_EVENT_STREAM).build();
        WebClient.create(httpConnector)
                .exchange(request)
                .flatMap(cr -> cr.bodyToFlux(String.class))
                .doOnNext(a -> {
                    LOG.info("Received: {}", a);
                }).collectList().block();
    }
}
