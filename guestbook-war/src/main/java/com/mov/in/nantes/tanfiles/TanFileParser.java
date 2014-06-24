/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mov.in.nantes.tanfiles;

import au.com.bytecode.opencsv.CSVReader;
import com.mov.in.nantes.jsontan.Etapes;
import com.mov.in.nantes.jsontan.ShapesTraject;
import com.mov.in.nantes.jsontan.Traject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author etienne
 */
public class TanFileParser {

    public ArrayList<ArrayList> getTraject(Traject traject) throws FileNotFoundException, IOException {

        FileInputStream stream = new FileInputStream("json/trips.csv");
        CSVReader reader = new CSVReader(new InputStreamReader(stream, "UTF-8"));
        String[] nextLine;
        List lineShapes = new ArrayList<String>();
        List steps = new ArrayList<String>();

        while ((nextLine = reader.readNext()) != null) {

            for (int i = 0; i < traject.getEtapes().size(); i++) {
                Etapes etape = (Etapes) ((ArrayList) traject.getEtapes()).get(i);

                if (!etape.isMarche() && !steps.contains(etape)) {
                    String nomLigne = nextLine[3].replaceAll("(.*)\\s-\\s(.*)", "$1-$2");
                    nomLigne = nomLigne.replaceAll("(.*)\\s/\\s(.*)", "$1/$2");
                    if (nomLigne.contains(etape.getLigne().getTerminus()) && nextLine[0].substring(0, etape.getLigne().getNumLigne().length() + 1).contains(etape.getLigne().getNumLigne())) {
                        lineShapes.add(nextLine[6]);
                        steps.add(etape);
                    }
                }
            }
        }

        ArrayList<ArrayList> shapes = getShapes(lineShapes, traject);
        return shapes;
    }

    private ArrayList<ArrayList> getShapes(List shapes, Traject traject) throws UnsupportedEncodingException, FileNotFoundException, IOException {

        FileInputStream stream = new FileInputStream("json/shapes.csv");
        CSVReader reader = new CSVReader(new InputStreamReader(stream, "UTF-8"));
        String[] nextLine;
        ArrayList<ArrayList> allTraject = new ArrayList<ArrayList>();
        HashMap<Integer, ShapesTraject> trajectStart = new HashMap<Integer, ShapesTraject>();
        HashMap<Integer, ShapesTraject> trajectEnd = new HashMap<Integer, ShapesTraject>();

        for (int i = 0; i < shapes.size(); i++) {
            ArrayList<ShapesTraject> shapesArray = new ArrayList<ShapesTraject>();
            allTraject.add(i, shapesArray);
        }

        List positionsStopArray = getPositionsStop(traject);
        while ((nextLine = reader.readNext()) != null) {

            for (int i = 0; i < shapes.size(); i++) {
                if (nextLine[0].equals(shapes.get(i))) {
                    List positionsStop = (ArrayList) positionsStopArray.get(i);
                    List positionsStopstart = (ArrayList) positionsStop.get(0);
                    List positionsStopend = (ArrayList) positionsStop.get(1);

                    ShapesTraject st = new ShapesTraject(nextLine[1], nextLine[2]);
                    ArrayList<ShapesTraject> shapesArray = (ArrayList) allTraject.get(i);
                    shapesArray.add(st);
                    if (positionsStopstart.contains(nextLine[1] + "" + nextLine[2])) {
                        trajectStart.put(i, st);
                    } else if (positionsStopend.contains(nextLine[1] + "" + nextLine[2])) {
                        trajectEnd.put(i, st);
                    }
                }
            }
        }
        for (int i = 0; i < shapes.size(); i++) {
            ArrayList<ShapesTraject> shapesArray = (ArrayList) allTraject.get(i);
            int indexStart = shapesArray.indexOf(trajectStart.get(i));
            int indexEnd = shapesArray.indexOf(trajectEnd.get(i));

            if (indexStart < indexEnd) {
                allTraject.set(i,new ArrayList<ShapesTraject>(shapesArray.subList(indexStart, indexEnd)));
            } else {
                 allTraject.set(i,new ArrayList<ShapesTraject>(shapesArray.subList(indexEnd, indexStart)));
            }
        }
        return allTraject;
    }

    private List getPositionsStop(Traject traject) throws FileNotFoundException, UnsupportedEncodingException, IOException {

        FileInputStream stream = new FileInputStream("json/stops.csv");
        CSVReader reader = new CSVReader(new InputStreamReader(stream, "UTF-8"));
        String[] nextLine;
        List stopArray = new ArrayList<ArrayList>();

        int marcheCount = 0;
        for (int i = 0; i < traject.getEtapes().size(); i++) {
            if (i + 2 < traject.getEtapes().size()) {
                if (!((Etapes) traject.getEtapes().toArray()[i + 1]).isMarche()) {
                    List stopsByPoints = new ArrayList<ArrayList>();
                    stopsByPoints.add(new ArrayList<String>());
                    stopsByPoints.add(new ArrayList<String>());
                    stopArray.add(i - marcheCount, stopsByPoints);
                } else {
                    marcheCount++;
                }
            }
        }

        while ((nextLine = reader.readNext()) != null) {
            marcheCount = 0;
            for (int i = 0; i < traject.getEtapes().size(); i++) {
                if (i + 2 < traject.getEtapes().size()) {
                    Etapes etape1 = (Etapes) traject.getEtapes().toArray()[i];
                    Etapes etape2 = (Etapes) traject.getEtapes().toArray()[i + 1];
                    if (!etape2.isMarche()) {
                        if (nextLine[1].equals(etape1.getArretStop().getLibelle())) {
                            List stopStartPositionsArray = (ArrayList) stopArray.get(i - marcheCount);
                            List stopStartPositions = (ArrayList) stopStartPositionsArray.get(0);
                            stopStartPositions.add(nextLine[3] + "" + nextLine[4]);
                        } else if (nextLine[1].equals(etape2.getArretStop().getLibelle())) {
                            List stopEndPositionsArray = (ArrayList) stopArray.get(i - marcheCount);
                            List stopEndPositions = (ArrayList) stopEndPositionsArray.get(1);
                            stopEndPositions.add(nextLine[3] + "" + nextLine[4]);
                        }
                    } else {
                        marcheCount++;
                    }
                }
            }
        }
        return stopArray;
    }
}
