package pl.sparkbit.model;

import java.beans.ConstructorProperties;

/**
 * Created by chanter on 13.12.2016.
 */
public class Address {

    public String getCity() {
        return city;
    }

    private final String city;

    @ConstructorProperties(value = {"city"})
    public Address(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                '}';
    }
}
