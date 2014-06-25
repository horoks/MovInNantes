/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.move.in.nantes.favorites;

import org.codehaus.jackson.map.ObjectMapper;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
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
public class RetrieveFavoritesServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Récupération de l'utilisateur courant
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        // Récupération du DataStore
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        // Filtre sur l'utilisateur courant
        Filter userIdFilter = new FilterPredicate("userId", FilterOperator.EQUAL, user.getUserId());
        
        // Requête de récupération des trajets de l'utilisateur courant
        Query query = new Query("Traject").setFilter(userIdFilter);
        
        // Recherche des résultats
        PreparedQuery pq = datastore.prepare(query);
        
        List<SimplyTraject> listTrajects = new ArrayList<SimplyTraject>();
        // Parcours des résultats
        for(Entity result : pq.asIterable()) {
            String departure = (String) result.getProperty("departure");
            String arrival = (String) result.getProperty("arrival");
            
            listTrajects.add(new SimplyTraject(departure, arrival));
        }
        
        // Conversion de la liste de résultats en json puis envoi
        ObjectMapper objectMapper = new ObjectMapper();
        
        response.setContentType("application/json");
        
        String resp = objectMapper.writeValueAsString(listTrajects);
        response.getWriter().println(resp);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to retrieve favorites trajects to a user";
    }
}
