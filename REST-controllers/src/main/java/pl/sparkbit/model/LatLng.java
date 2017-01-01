package pl.sparkbit.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.beans.ConstructorProperties;

/**
 * Created by chanter on 14.12.2016.
 */
public class LatLng {

    @Min(-90)
    @Max(90)
    private final Double lat;
    private final Double lng;

    @ConstructorProperties({"lat", "lng"})
    public LatLng(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return "LatLng{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
