/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.move.in.nantes.cars;

import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bastien
 */
public class ParkingServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("latitude") != null && request.getParameter("longitude") != null) {
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");
            
            List<Parking> listNearParkings = ParkingParser.getNearParkings(latitude, longitude);
            List<Parking> listToJson = new ArrayList<Parking>();
            int i = 0;
            while(listNearParkings.get(i) != null && i < 3) {
                listToJson.add(listNearParkings.get(i));
                i++;
            }
            
            ObjectMapper objectMapper = new ObjectMapper();
            
            response.setContentType("application/json");
            
            response.getWriter().println(objectMapper.writeValueAsString(listToJson));
        }else{
            response.getWriter().println(request.getParameter("latitude")+request.getParameter("longitude"));
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Get Parking location";
    }// </editor-fold>

}
