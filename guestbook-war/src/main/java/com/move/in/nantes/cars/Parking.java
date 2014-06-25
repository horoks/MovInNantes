/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.move.in.nantes.cars;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Bastien
 */
public class Parking implements Comparable<Parking> {

    @JsonProperty("nameParking")
    String nameParking;
   
    @JsonProperty("coordinates")
    Coordinates coordinates;
    
    @JsonProperty("postalCodeParking")
    String postalCodeParking;
    
    @JsonProperty("cityParking")
    String cityParking;
    
    @JsonProperty("addressParking")
    String addressParking;
    
    double distance;
    
    public Parking(String name, Coordinates coordinates, String postalCode, String city, String address) {
        this.nameParking = name;
        this.coordinates = coordinates;
        this.postalCodeParking = postalCode;
        this.cityParking = city;
        this.addressParking = address;
    }

    public String getName() {
        return nameParking;
    }

    public String getLatitude() {
        return coordinates.getLatitude();
    }

    public String getLongitude() {
        return coordinates.getLongitude();
    }

    public String getPostalCode() {
        return postalCodeParking;
    }

    public String getCity() {
        return cityParking;
    }

    public String getAddress() {
        return addressParking;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int compareTo(Parking p) {
        return Double.valueOf(distance).compareTo(p.getDistance());
    }
}
