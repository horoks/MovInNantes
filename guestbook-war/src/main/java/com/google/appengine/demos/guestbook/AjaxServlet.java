package com.google.appengine.demos.guestbook;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AjaxServlet extends HttpServlet {

    private final String USER_AGENT = "Mozilla/5.0";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Hello");
        try {
            URL url = new URL("https://api.myserver.com/v1/comments");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Custom-Header", "xxx");
            connection.setRequestProperty("Content-Type", "application/json");

            // POST the http body data
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("{\"comment\": \"awesome tutorial\"}");
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // OK
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuffer res = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    res.append(line);
                }
                reader.close();

                JSONObject jsonObj = new JSONObject(res);
                String count = jsonObj.getInt("count");
             //   resp.getWriter().println(response.toString());

            } 

        } catch (Exception e) {
        }

        //print result
        
    }

}
