var map;
var panel;
var initialize;
var calculate;
var direction;
var directionDisplay;
var destination;
var latLng = new google.maps.LatLng(47.2092934, -1.4866354000000683);

var markers = new Object();
markers["created"] = new Array();
markers["currentMarker"] = null;
markers["currentWindow"] = null;
markers["closeWindow"] = function(map) {
	if(markers.currentWindow != null)
	{
		markers.currentWindow.close(map, markers.currentMarker);
		markers.currentMarker = null;
		markers.currentWindow = null;
	}
};
markers["openWindow"] = function(map, marker, window) {
	markers.closeWindow(map);
	window.open(map,marker);
	markers.currentMarker = marker;
	markers.currentWindow = window;
}; 

function adapterZoom(map, markers) {
	if(markers.created.length == 1) {
		map.panTo(new google.maps.LatLng(markers.created[0].getPosition().lat(), markers.created[0].getPosition().lng()));
		map.setZoom(12);
	}
	else if(markers.created.length > 1) {
		var lat_min = markers.created[0].getPosition().lat();
		var lat_max = markers.created[0].getPosition().lat();
		var lng_min = markers.created[0].getPosition().lng();
		var lng_max = markers.created[0].getPosition().lng();
		for(var i=1 ; i < markers.created.length ; i++)
		{
			if(lat_min > markers.created[i].getPosition().lat())
				lat_min = markers.created[i].getPosition().lat();
			if(lat_max < markers.created[i].getPosition().lat())
				lat_max = markers.created[i].getPosition().lat();
			if(lng_min > markers.created[i].getPosition().lng())
				lng_min = markers.created[i].getPosition().lng();
			if(lng_max < markers.created[i].getPosition().lng())
				lng_max = markers.created[i].getPosition().lng();
		}
		
		map.panTo(new google.maps.LatLng(
			((lat_max + lat_min) / 2.0),
			((lng_max + lng_min) / 2.0)
		));
		
		map.fitBounds(new google.maps.LatLngBounds(
			new google.maps.LatLng(lat_min, lng_min),
			new google.maps.LatLng(lat_max, lng_max)
		));
	}
}

initialize = function() {
    // Correspond au coordonnées de Lille
    var myOptions = {
        zoom: 14, // Zoom par défaut
        center: latLng, // Coordonnées de départ de la carte de type latLng 
        mapTypeId: google.maps.MapTypeId.ROADMAP, // Type de carte, différentes valeurs possible HYBRID, ROADMAP, SATELLITE, TERRAIN
        maxZoom: 20,
        disableDefaultUI: true,
        scrollwheel: true,
        zoomControl: true,
        zoomControlOptions: {
            style: google.maps.ZoomControlStyle.SMALL
        },
        styles: [
            { stylers: [{ saturation: -100 }, { gamma: 1 }] },
            { elementType: "labels.text.stroke", stylers: [{ visibility: "off" }] },
            { featureType: "poi.business", elementType: "labels.text", stylers: [{ visibility: "off" }] },
            { featureType: "poi.business", elementType: "labels.icon", stylers: [{ visibility: "off" }] },
            { featureType: "poi.place_of_worship", elementType: "labels.text", stylers: [{ visibility: "off" }] },
            { featureType: "poi.place_of_worship", elementType: "labels.icon", stylers: [{ visibility: "off" }] },
            { featureType: "road", elementType: "geometry", stylers: [{ visibility: "simplified" }] },
            { featureType: "water", stylers: [{ visibility: "on" }, { saturation: 50 }, { gamma: 0 }, { hue: "#50a5d1" }] },
            { featureType: "administrative.neighborhood", elementType: "labels.text.fill", stylers: [{ color: "#333333" }] },
            { featureType: "road.local", elementType: "labels.text", stylers: [{ weight: 0.5 }, { color: "#333333" }] },
            { featureType: "transit.station", elementType: "labels.icon", stylers: [{ gamma: 1 }, { saturation: 50 }] }
          ]
    };

    var pinColor = "9acd34";

    map = new google.maps.Map(document.getElementById('map'), myOptions);


    var marker = new google.maps.Marker({
        position: latLng,
        map: map,
        title: "Vous etes ici",
        type : "first",
    });
    markers.created.push(marker);

    direction = new google.maps.DirectionsRenderer({
        map: map,
        panel: document.getElementById("panel")
    });

    
    adapterZoom(map, markers);
    
};


calculate = function() {
	
    var travelmode;
    origin = jQuery('#origine').val(); // Le point départ
    destination = jQuery('#destination').val(); // Le point d'arrivé
    /*         
    switch(jQuery('input[name=travelMode]:checked').val()){
        case "driving" :
            travelmode = google.maps.DirectionsTravelMode.DRIVING;
            break;
        case "bicycling" :
            travelmode = google.maps.DirectionsTravelMode.BICYCLING;
            break;
        case "walking" :
            travelmode = google.maps.DirectionsTravelMode.WALKING;
            break;
    }
    */
    travelmode = google.maps.DirectionsTravelMode.DRIVING;
    if (origin && destination) {
        var request = {
            origin: origin,
            destination: destination,
            durationInTraffic: true,
            travelMode: travelmode // Type de transport
        };
        
        var directionsService = new google.maps.DirectionsService(); // Service de calcul d'itinéraire
        directionsService.route(request, function(response, status) { // Envoie de la requête pour calculer le parcours
            if (status == google.maps.DirectionsStatus.OK) {
            	console.log(response);
                direction.setOptions( { suppressMarkers: true } );
                direction.setDirections(response); // Trace l'itinéraire sur la carte et les différentes étapes du parcours
            }
        });
    }
    
};

function ajoutGarage(map, markers, lat, lng, title, link, adress,post_id,terms,icon,image) {
    var marker = placeMarker(map, lat, lng, title, terms, icon);
    // Options de la fenêtre
    var windowOptions = {
        content: 
                '<div style="height:120px; min-width:240px;">' +
                '<div class="col-3">' + image + '</div>' +
                '<div class="col-9"><span class="garage-name">' + title + '</span><br/>' +
                '<span class="adress">' + adress + '</span><br/></div>' +
                '</div>'
                
    };

    // Création de la fenêtre
    var infoWindow = new google.maps.InfoWindow(windowOptions);

    // Affichage de la fenêtre au click sur le marker
    google.maps.event.addListener(marker, 'click', function() {
        document.getElementById('destination').value = marker.position;
        calculate();
        markers.openWindow(map, marker, infoWindow);
    });
    // Fermeture de la fenêtre au click sur la map
    google.maps.event.addListener(map, 'click', function() {
        markers.closeWindow(map);
    });
    
    jQuery('.mark_'+post_id).click(function(){
        document.getElementById('destination').value = marker.position;
        calculate();
        adapterZoom(map, markers);
        markers.openWindow(map, marker, infoWindow);
    });
    
    markers.created.push(marker);
}

function placeMarker(map, lat, lng, title,terms,icon) {
    var args = {
        position: new google.maps.LatLng(lat, lng),
        map: map,
        title: title,
        type : terms
    };
    if (icon != 'default' && icon != '')
        args['icon'] = icon;
    return new google.maps.Marker(args);
}

initialize();
