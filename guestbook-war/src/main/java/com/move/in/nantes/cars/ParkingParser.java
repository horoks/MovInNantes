package com.move.in.nantes.cars;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Bastien
 */
public class ParkingParser {

    private static final Logger log = Logger.getLogger(ParkingParser.class.getName());
    
    private static Coordinates getCoordinates(String coord) {
        coord = coord.substring(1);                     // remove the "["
        coord = coord.substring(0, coord.length() - 2); // remove the "]"

        int comma = coord.indexOf(",");

        String latitude = coord.substring(0, comma - 1).trim();
        String longitude = coord.substring(comma + 1, coord.length() - 1).trim();

        return new Coordinates(latitude, longitude);
    }

    private static List<Parking> parseXml() {
        List<Parking> listPakings = new ArrayList<Parking>();
        
        try {
            SAXBuilder saxb = new SAXBuilder();
            File file = new File("WEB-INF/json/Parking.xml");
            
            Document doc = saxb.build(file);
            Element root = doc.getRootElement();
            List<Element> locations = root.getChildren("data").get(0).getChildren("element");
            
            for (int i = 0; i < locations.size(); i++) {
                Element elem = locations.get(i);

                if (elem.getChildText("CATEGORIE").equalsIgnoreCase("1001")) {

                    String name = elem.getChild("geo").getChildText("name");

                    Coordinates coordinates = getCoordinates(elem.getChildText("_l"));

                    String postalCode = elem.getChildText("CODE_POSTAL");
                    String city = elem.getChildText("COMMUNE");
                    String address = elem.getChildText("ADRESSE");
                    
                    listPakings.add(new Parking(name, coordinates, postalCode, city, address));
                }
            }
        } catch (JDOMException ex) {
            Logger.getLogger(ParkingParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParkingParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPakings;
    }
    
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    
    private static double getDistance(double fromLat, double fromLon, double toLat, double toLon) {
        double rFromLat = deg2rad(fromLat);
        double rFromLon = deg2rad(fromLon);
        double rToLat = deg2rad(toLat);
        double rToLon = deg2rad(toLon);
        
        double x = (rToLon - rFromLon) * Math.cos((rFromLat + rToLat) / 2);
        double y = rToLat - rFromLat;
        
        double dist = Math.sqrt((x*x) + (y*y)) * 6371009;
        return dist;
    }
    
    public static List<Parking> getNearParkings(String latitude, String longitude) {
        List<Parking> listParkings = parseXml();
        List<Parking> listNearParkings = new ArrayList<Parking>();
        
        double lat = Double.valueOf(latitude);
        double lon = Double.valueOf(longitude);
        
        for(int i = 0; i < listParkings.size(); i++) {
            Parking parking = listParkings.get(i);
            
            double distance = getDistance(lon, lat, Double.valueOf(parking.getLatitude()), Double.valueOf(parking.getLongitude()));
            //if(distance <= 500) {
                parking.setDistance(distance);
                listNearParkings.add(parking);
            //}
        }
        
        Collections.sort(listNearParkings);

        return listNearParkings;
    }
}
