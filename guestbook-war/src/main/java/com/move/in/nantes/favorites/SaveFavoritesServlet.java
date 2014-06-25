/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.move.in.nantes.favorites;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bastien
 */
public class SaveFavoritesServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("origine") != null && request.getParameter("destination") != null) {

            // Récupération des adresses de départ et d'arrivée
            String departure = request.getParameter("origine");
            String arrival = request.getParameter("destination");
            
            if (!departure.isEmpty() && arrival.isEmpty()) {
                // Récupération de l'utilisateur courant
                UserService userService = UserServiceFactory.getUserService();
                User user = userService.getCurrentUser();

                // Création de l'entité représentant le trajet
                Entity traject = new Entity("Traject");
                traject.setProperty("userId", user.getUserId());
                traject.setProperty("departure", departure);
                traject.setProperty("arrival", arrival);

                // Enregistrement de l'entité dans le datastore
                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                datastore.put(traject);
            }
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet used to save a favorite traject";
    }

}
