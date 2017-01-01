package pl.sparkbit.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.WebReactiveConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.sparkbit.model.Address;
import pl.sparkbit.model.LatLng;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

public class FunctionalWebFrameworkApplication implements WebReactiveConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(FunctionalWebFrameworkApplication.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        RouterFunction<?> route = RouterFunctions.route(POST("/location/address"), request ->
                ServerResponse.ok().body(request.bodyToFlux(LatLng.class).map(ll -> new Address("Rome")),
                        Address.class)
        ).and(RouterFunctions.route(GET("/location"),
                request -> ServerResponse.ok().body(Mono.just("home"), String.class)));
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter =
                new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create("localhost", 8080);
        server.newHandler(adapter).block();
        LOG.info("Press ENTER to exit.");
        System.in.read();
    }

}
