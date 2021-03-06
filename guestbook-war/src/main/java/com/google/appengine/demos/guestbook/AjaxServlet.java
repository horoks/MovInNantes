package com.google.appengine.demos.guestbook;

import com.mov.in.nantes.jsontan.Traject;
import com.mov.in.nantes.tanfiles.TanFileParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

public class AjaxServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 10);
        
        String depart = URLEncoder.encode(req.getParameter("depart"), "UTF-8");
        String arrive = URLEncoder.encode(req.getParameter("arrive"), "UTF-8");
        String type = URLEncoder.encode("0", "UTF-8");
        String accessible = URLEncoder.encode("0", "UTF-8");
        String temps = URLEncoder.encode(dateFormat.format(cal.getTime()), "UTF-8");
        String retour = URLEncoder.encode("0", "UTF-8");

        try {
            URL url = new URL("https://www.tan.fr/ewp/mhv.php/itineraire/resultat.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

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

                Iterator i = trajets.iterator();
                resp.setContentType("text/plain");
                Traject t = (Traject) i.next();
                TanFileParser tfp = new TanFileParser();

                resp.getWriter().println(objectMapper.writeValueAsString(tfp.getTraject(t)));
                //   resp.getWriter().println(objectMapper.writeValueAsString(t.getEtapes()));
                //resp.getWriter().println(connection.getResponseCode());
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
