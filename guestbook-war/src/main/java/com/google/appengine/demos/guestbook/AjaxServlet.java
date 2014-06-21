package com.google.appengine.demos.guestbook;

import com.mov.in.nantes.jsontan.Traject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
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

public class AjaxServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String depart = URLEncoder.encode("Address|ADDRESS11524|JULES VERNE|Nantes||boulevard|307521|2256362", "UTF-8");
        String arrive = URLEncoder.encode("Address|ADDRESS11910|MAIL PABLO PICASSO|Nantes|||306856|2253442", "UTF-8");
        String type = URLEncoder.encode("0", "UTF-8");
        String accessible = URLEncoder.encode("0", "UTF-8");
        String temps = URLEncoder.encode("2014-06-26 14:55", "UTF-8");
        String retour = URLEncoder.encode("0", "UTF-8");

        try {
            URL url = new URL("https://www.tan.fr/ewp/mhv.php/itineraire/resultat.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            String query = String.format("depart=%s&arrive=%s&type=%s&accessible=%s&temps=%s&retour=%s",
                    depart, arrive, type, accessible, temps, retour);
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
                ObjectMapper objectMapper = new ObjectMapper();

                //convert json string to object
                Collection<Traject> trajets = objectMapper.readValue(response, TypeFactory.defaultInstance().constructParametricType(
                        Collection.class, Traject.class));

                resp.setContentType("text/plain");
                //  resp.getWriter().println(gson.toJson(enums));
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
