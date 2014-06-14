<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <head>
        <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
        <script type="text/javascript" src="/scripts/jquery-1.11.1.min.js"></script>
    </head>

    <body>
        <div id="page" class="bg-nantes">
            <header class="header-menu"></header>
            <section class="row ">
                <%
                    String guestbookName = request.getParameter("guestbookName");
                    if (guestbookName == null) {
                        guestbookName = "default";
                    }
                    pageContext.setAttribute("guestbookName", guestbookName);
                    UserService userService = UserServiceFactory.getUserService();
                    User user = userService.getCurrentUser();
                    if (user != null) {
                        pageContext.setAttribute("user", user);
                %>

                <p>Hello, ${fn:escapeXml(user.nickname)}! (You can
                    <a href="<%= userService.createLogoutURL(request.getRequestURI())%>">sign out</a>.)</p>
                    <%
                    } else {
                    %>
                <p>Hello!
                    <a href="<%= userService.createLoginURL(request.getRequestURI())%>">Sign in</a>
                    to include your name with greetings you post.</p>
                    <%
                        }
                    %>

                <%
                    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                    Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
                    // Run an ancestor query to ensure we see the most up-to-date
                    // view of the Greetings belonging to the selected Guestbook.
                    Query query = new Query("Greeting", guestbookKey).addSort("date", Query.SortDirection.DESCENDING);
                    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
                    if (greetings.isEmpty()) {
                %>
                <p>Guestbook '${fn:escapeXml(guestbookName)}' has no messages.</p>
                <%
                } else {
                %>
                <p>Messages in Guestbook '${fn:escapeXml(guestbookName)}'.</p>
                <%
                    for (Entity greeting : greetings) {
                        pageContext.setAttribute("greeting_content",
                                greeting.getProperty("content"));
                        if (greeting.getProperty("user") == null) {
                %>
                <p>An anonymous person wrote:</p>
                <%
                } else {
                    pageContext.setAttribute("greeting_user",
                            greeting.getProperty("user"));
                %>
                <p><b>${fn:escapeXml(greeting_user.nickname)}</b> wrote:</p>
                <%
                    }
                %>
                <blockquote>${fn:escapeXml(greeting_content)}</blockquote>
                <%
                        }
                    }
                %>

                <div>
                    <input id="origine" type="text" name="origine" value=""/>
                    <input id="destination" type="text" name="destination" value=""/>

                    <input id="voiture" type="radio" name="travelMode" value="driving" checked=""><label for="voiture">En voiture</label>            
                    <input id="pied" type="radio" name="travelMode" value="walking"><label for="pied">A pied</label>
                    <input id="velo" type="radio" name="travelMode" value="bicycling"><label for="velo">A vélo</label>
                    <button id="calculroute" > Click gros !</button>
                </div>

                <div id="map" style="height:500px;"></div>

            </section>
            <footer></footer>
        </div>
        <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
        <script type="text/javascript" src="/scripts/functions.js"></script>
        <script>
            jQuery("#calculroute").click(function() {
                calculate();
            });

            jQuery("#calculroute").click(function() {
                $.get("/test", function(data) {
                    $(".result").html(data);
                    alert("Load was performed.");
                });
            });
        </script>
    </body>
</html>