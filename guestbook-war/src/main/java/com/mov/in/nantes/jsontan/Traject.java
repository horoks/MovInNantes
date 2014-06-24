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

    public int getId() {
        return id;
    }

    public String getAdresseDepart() {
        return adresseDepart;
    }

    public String getAdresseArrivee() {
        return adresseArrivee;
    }

    public String getHeureDepart() {
        return heureDepart;
    }

    public String getHeureArrivee() {
        return heureArrivee;
    }

    public String getJourDepart() {
        return jourDepart;
    }

    public String getJourArrivee() {
        return jourArrivee;
    }

    public String getDuree() {
        return duree;
    }

    public String getCorrespondance() {
        return correspondance;
    }

    public String getChaineDepart() {
        return chaineDepart;
    }

    public String getChaineArrivee() {
        return chaineArrivee;
    }

    public String getFavoriDepart() {
        return favoriDepart;
    }

    public String getFavoriDepartVille() {
        return favoriDepartVille;
    }

    public String getFavoriDepartCP() {
        return favoriDepartCP;
    }

    public String getFavoriArrivee() {
        return favoriArrivee;
    }

    public String getFavoriArriveeVille() {
        return favoriArriveeVille;
    }

    public String getFavoriArriveeCP() {
        return favoriArriveeCP;
    }

    public String getAccessible() {
        return accessible;
    }

    public ArretDepart getArretDepart() {
        return arretDepart;
    }

    public Collection<Etapes> getEtapes() {
        return etapes;
    }
    
    
}
