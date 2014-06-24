/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mov.in.nantes.tan;

import com.mov.in.nantes.jsontan.ResponsePlace;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

/**
 *
 * @author etienne
 */
public class AdressTanservlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String adress = URLEncoder.encode(req.getParameter("adress"), "UTF-8");

        try {
            URL url = new URL("https://www.tan.fr/ewp/mhv.php/itineraire/address.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String query = String.format("nom=%s&prefix=depart",
                    adress);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(query);
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String inputLine;
                String response = new String();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                while ((inputLine = reader.readLine()) != null) {
                    response += inputLine;
                }
                reader.close();
                //create ObjectMapper instance
//                ObjectMapper objectMapper = new ObjectMapper();
//
                //convert json string to object
//                Collection<ResponsePlace> reponseplaces = objectMapper.readValue(response, TypeFactory.defaultInstance().constructParametricType(
//                        Collection.class, ResponsePlace.class));
                resp.setContentType("application/json");
                resp.getWriter().println(response);
            } else {
                resp.setContentType("text/plain");
                resp.getWriter().println(connection.getResponseCode());
            }
        } catch (MalformedURLException e) {
            resp.setContentType("text/plain");
            resp.getWriter().println(e.toString());
        } catch (IOException e) {
            resp.setContentType("text/plain");
            resp.getWriter().println(e.toString());
        }

    }

}
