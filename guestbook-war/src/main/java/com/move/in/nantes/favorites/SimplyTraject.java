/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.move.in.nantes.favorites;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Bastien
 */
public class SimplyTraject {
    
    @JsonProperty("departure")
    String departure;
    
    @JsonProperty("arrival")
    String arrival;
    
    public SimplyTraject(String departure, String arrival) {
        this.departure = departure;
        this.arrival = arrival;
    }
}
