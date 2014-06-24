/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mov.in.nantes.jsontan;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author etienne
 */
public class Ligne {

    @JsonProperty("numLigne")
    String numLigne;
    @JsonProperty("typeLigne")
    String typeLigne;
    @JsonProperty("terminus")
    String terminus;

    public String getNumLigne() {
        return numLigne;
    }

    public String getTypeLigne() {
        return typeLigne;
    }

    public String getTerminus() {
        return terminus;
    }
    
    
}
