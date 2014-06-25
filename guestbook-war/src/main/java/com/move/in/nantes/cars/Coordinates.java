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
public class Coordinates {
    
    @JsonProperty("latitude")
    String latitude;
    
    @JsonProperty("longitude")
    String longitude;

    public Coordinates(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
