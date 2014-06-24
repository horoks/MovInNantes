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
class Lieux {

    @JsonProperty("id")
    String id;
    @JsonProperty("nom")
    String nom;
    @JsonProperty("cp")
    String cp;
    @JsonProperty("ville")
    String ville;
    
}
