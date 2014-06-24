/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mov.in.nantes.jsontan;

import java.util.Collection;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author etienne
 */
public class ResponsePlace {

    @JsonProperty("typeLieu")
    String typeLieu;
    @JsonProperty("lieux")
    Collection<Lieux> lieux;

}
