/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mov.in.nantes.jsontan;

import java.util.ArrayList;
import java.util.Collection;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author etienne
 */
public class Traject {

    int id;
    @JsonProperty("adresseDepart")
    String adresseDepart;
    @JsonProperty("adresseArrivee")
    String adresseArrivee;
    @JsonProperty("heureDepart")
    String heureDepart;
    @JsonProperty("heureArrivee")
    String heureArrivee;
    @JsonProperty("jourDepart")
    String jourDepart;
    @JsonProperty("jourArrivee")
    String jourArrivee;
    @JsonProperty("duree")
    String duree;
    @JsonProperty("correspondance")
    String correspondance;
    @JsonProperty("chaineDepart")
    String chaineDepart;
    @JsonProperty("chaineArrivee")
    String chaineArrivee;
    @JsonProperty("favoriDepart")
    String favoriDepart;
    @JsonProperty("favoriDepartVille")
    String favoriDepartVille;
    @JsonProperty("favoriDepartCP")
    String favoriDepartCP;
    @JsonProperty("favoriArrivee")
    String favoriArrivee;
    @JsonProperty("favoriArriveeVille")
    String favoriArriveeVille;
    @JsonProperty("favoriArriveeCP")
    String favoriArriveeCP;
    @JsonProperty("accessible")
    String accessible;
    @JsonProperty("arretDepart")
    ArretDepart arretDepart;
    @JsonProperty("etapes")
    Collection<Etapes> etapes;
}
