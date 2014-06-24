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
public class Etapes {

    @JsonProperty("marche")
    Boolean marche;
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

    public Boolean isMarche() {
        return marche;
    }

    public String getHeureDepart() {
        return heureDepart;
    }

    public String getHeureArrivee() {
        return heureArrivee;
    }

    public String getDuree() {
        return duree;
    }

    public ArretStop getArretStop() {
        return arretStop;
    }

    public Ligne getLigne() {
        return ligne;
    }

}
