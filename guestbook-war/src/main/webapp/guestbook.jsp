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
        <link rel="stylesheet" href="/stylesheets/jquery.pageslide.css">

    </head>

    <body>
        <div id="page" class="bg-nantes">

            <header class="header-menu">
                <a href="#modal" class="search"></a>
                <%
                    UserService userService = UserServiceFactory.getUserService();
                    User user = userService.getCurrentUser();
                    if (user != null) {
                        pageContext.setAttribute("user", user);
                %>
                <p>${fn:escapeXml(user.nickname)}! (
                    <a href="<%= userService.createLogoutURL(request.getRequestURI())%>">Se déconnecter</a>.)</p>
                    <%
                    } else {
                    %>
                <p><a href="<%= userService.createLoginURL(request.getRequestURI())%>">Se connecter avec votre compte Google.</a></p>
                <%
                    }
                %>
                <div id="result-display"></div>
            </header>
            <div id="modal" style="display:none">
                <div class="search-bar">
                    <input id="origine" type="text" name="origine" placeholder="Adresse de départ" value=""/>
                    <input id="destination" type="text" name="destination" placeholder="Adresse de déstination" value=""/>
                    <input id="voiture" type="radio" name="travelMode" value="driving" checked=""><label for="voiture">En voiture</label>            
                    <input id="pied" type="radio" name="travelMode" value="walking"><label for="pied">A pied</label>
                    <input id="velo" type="radio" name="travelMode" value="bicycling"><label for="velo">A vélo</label>
                    <input id="tan" type="radio" name="travelMode" value="tan"><label for="tan">Transport en commun</label>
                    <button id="calculroute" > rechercher</button>
                </div>
                <% if (user != null) { %>
                <div id="fav">
                    <h2>Favoris</h2>
                    <button class="save_fav" >Sauvegarder</button>
                </div>
                <% }  %>    
            </div>
            <section>
                <div id="map" style="height:100%;"></div>
            </section>
            <footer></footer>
        </div>
        <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
        <script type="text/javascript" src="/scripts/functions.js"></script>
        <script src="/scripts/jquery.pageslide.min.js"></script>
        <script>
            <% if (user != null) { %>
            $.get("/RetrieveFavoritesServlet", function(data) {
                for (var i in data){
                    $("#fav").append("<p>De : "+data[i].departure+"</p>");
                    $("#fav").append("<p>A : "+data[i].arrival+"</p><br/>");
                }
            });
            <% }%>
            $(".search").pageslide();
            jQuery("#calculroute").click(function() {
                calculate();
            });

            jQuery(".save_fav").click(function() {
                $.post("/SaveFavoritesServlet", {origine: jQuery('#pageslide #origine').val(), destination: jQuery('#pageslide #destination').val()});
            });
        </script>
    </body>
</html>