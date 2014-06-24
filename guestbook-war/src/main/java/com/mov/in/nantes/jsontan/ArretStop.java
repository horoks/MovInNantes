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
public class ArretStop {

    @JsonProperty("libelle")
    String libelle;
    @JsonProperty("accessible")
    String accessible;
    @JsonProperty("danger")
    String danger;

    public String getLibelle() {
        return libelle;
    }

    public String getAccessible() {
        return accessible;
    }

    public String getDanger() {
        return danger;
    }
    
    
}
