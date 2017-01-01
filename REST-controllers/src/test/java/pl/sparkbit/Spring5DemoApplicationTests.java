package pl.sparkbit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import pl.sparkbit.model.Address;
import pl.sparkbit.model.LatLng;
import pl.sparkbit.server.LocationController;
import pl.sparkbit.server.Spring5DemoApplication;
import reactor.core.publisher.Flux;
import reactor.test.subscriber.ScriptedSubscriber;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {LocationController.class, Spring5DemoApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Spring5DemoApplicationTests {

    private WebClient webClient;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        this.webClient = WebClient.create(new ReactorClientHttpConnector());
    }

    @Test
    public void homeController() {
        LatLng ll = new LatLng(20d, 52d);

        ClientRequest<Flux<LatLng>> request = ClientRequest.POST("http://localhost:{port}/location/address", this.port)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.just(ll), LatLng.class);

        Flux<Address> result = this.webClient
                .exchange(request)
                .flatMap(response -> response.bodyToFlux(Address.class));

        ScriptedSubscriber.<Address>create()
                .consumeNextWith(address -> {
                    assertEquals("Rome", address.getCity());
                })
                .expectComplete()
                .verify(result);
    }
}
