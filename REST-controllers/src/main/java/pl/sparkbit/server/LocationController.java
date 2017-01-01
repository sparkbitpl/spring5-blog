package pl.sparkbit.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.sparkbit.model.Address;
import pl.sparkbit.model.LatLng;
import reactor.core.publisher.Flux;

import javax.validation.Valid;


/**
 * Created by chanter on 12.12.2016.
 */
@RestController
public class LocationController {

    private static final Logger LOG = LoggerFactory.getLogger(LocationController.class);


    private final ReactiveLocationService locationService;

    @Autowired
    public LocationController(ReactiveLocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * Produces and consumes JSON in a reactive fashion
     */
    @PostMapping(path = "location", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void logLocation(@Valid @RequestBody Flux<LatLng> locations) {
        LOG.info("Received request");
        locationService.storeCoordinates(locations);
        LOG.info("Request processed");
    }

    /**
     * Produces and consumes JSON in a reactive fashion
     */
    @PostMapping(path = "location/address", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Address> logLocation2(@Valid @RequestBody Flux<LatLng> locations) {
        LOG.info("Received request");
        return locations.map(ll -> new Address("Rome"));
    }

    @PostMapping(path = "locationError", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Address> errorExample(@Valid @RequestBody Flux<LatLng> locations) {
        throw new IllegalArgumentException("Bad path!");
    }


//    @ExceptionHandler
//    public Publisher<String> handleException(Exception e) {
//        LOG.error("Error! {}", e);
//        return Mono.just("Oops. Something went wrong!");
//    }

    @GetMapping(path = "location", produces = "text/plain")
    public String getLocation() {
        return "You are somewhere!";
    }
}
