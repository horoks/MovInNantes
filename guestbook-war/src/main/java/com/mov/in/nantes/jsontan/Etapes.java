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
class Etapes {

    @JsonProperty("marche")
    String marche;
    @JsonProperty("heureDepart")
    String heureDepart;
    @JsonProperty("heureArrivee")
    String heureArrivee;
    @JsonProperty("duree")
    String duree;
    @JsonProperty("arretStop")
    ArretStop arretStop;
    @JsonProperty("ligne")
    Ligne ligne;
}
