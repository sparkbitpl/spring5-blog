package pl.sparkbit.client;


import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Created by chanter on 14.12.2016.
 */
public class GetLocationClient {

    public static void main(String[] argv) {
        ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
        ClientRequest<Void> request = ClientRequest.GET("http://localhost:8080/location")
                .accept(MediaType.TEXT_PLAIN).build();
        WebClient.create(httpConnector)
                .exchange(request)
                .flatMap(cr -> cr.bodyToFlux(String.class))
                .doOnNext(System.out::println).collectList().block();
    }
}
