/***********
 * OU TM352 Block 3, TMA03: index.js
 *
 * To function correctly this file must be placed in a Cordova project and the appopriate plugins installed.
 * You need to complete the code which is commented with TODO.
 * This includes the FRs and a few other minor changes related to your HTML design decisions.
 *
 * Released by Chris Thomson / Stephen Rice: Dec 2020
 * Modified by Chris Thomson: November 2021
 * Modified and submitted by (Simon Lodge)
 ************/

/**
 * Please remember to install the cleartext plugin:
 *
 * cordova plugin add cordova-plugin-enable-cleartext-traffic
 * cordova prepare
 */



// Execute in strict mode to prevent some common mistakes
"use strict";

// Declare a ClientOrder object for use by the HTML view
var controller;
document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
    console.log("Running cordova-" + cordova.platformId + "@" + cordova.version);
    // Create the ClientOrder object for use by the HTML view
    controller = new ClientOrder();
}

// JavaScript "class" containing the model, providing controller "methods" for the HTML view
function ClientOrder() {
    console.log("Creating controller/model");

    // PRIVATE VARIABLES AND FUNCTIONS - available only to code inside the controller/model
    // Note these are declared as function functionName() { ... }
    const url = new URL('http://137.108.92.9/openstack/api/');

	// NOTE CORS: if you get a CORS error we may recommend you insert code at this point.
    // HERE Maps code, based on:
    // https://developer.here.com/documentation/maps/3.1.19.2/dev_guide/topics/map-controls-ui.html
    // https://developer.here.com/documentation/maps/3.1.19.2/dev_guide/topics/map-events.html

    // Initialize the platform object:
    var platform = new H.service.Platform({
        // TODO: Change to your own API key or map will NOT work!
        apikey: "iHGshl2g4UfVq-nMtxCS_aOFV8ne8HRgnkwXQtkEqIY",
    });
    // Obtain the default map types from the platform object:
    var defaultLayers = platform.createDefaultLayers();
    // Instantiate (and display) a map object:
    var map = new H.Map(
        document.getElementById("mapContainer"),
        defaultLayers.vector.normal.map,
        {
            zoom: 15,
            center: { lat: 52.5, lng: 13.4 },
        }
    );

    // Create the default UI:
    var ui = H.ui.UI.createDefault(map, defaultLayers);
    var mapSettings = ui.getControl("mapsettings");
    var zoom = ui.getControl("zoom");
    var scalebar = ui.getControl("scalebar");
    mapSettings.setAlignment("top-left");
    zoom.setAlignment("top-left");
    scalebar.setAlignment("top-left");
    // Enable the event system on the map instance:
    var mapEvents = new H.mapevents.MapEvents(map);
    // Instantiate the default behavior, providing the mapEvents object:
    new H.mapevents.Behavior(mapEvents);

    var icon = new H.map.DomIcon("<div>&#x1F3C3;</div>");
    var marker;
    var markers = [];

    // TODO Lookup an address and add a marker to the map at the position of this address
    function addMarkerToMap(address) {
        if (address != null) {
            console.log('Add marker function called');
            // Hint: If you call the OpenStreetMap REST API too frequently, your access will be blocked.
            //       We have provided a helper function to prevent this however if you open the app
            //       on several browser windows at once you may still run into problems.
            //       Consider hardcoding locations for testing.
            // Hint: To ensure a marker will be cleared by clearMarkersFromMap, use:
            //       markers.push(marker);
            //       to add it to the markers array
            var onSuccess = function (data) {
                // TODO 2(a) FR2.2
                // You need to implement this function
                // See the TMA for an explanation of the functional requirements

                var point = {
                    lat: data[0].lat,
                    lng: data[0].lon,
                };
                
                marker = new H.map.DomMarker(point, { icon: icon });
                map.addObject(marker);
                console.log('Marker added');
                markers.push(marker);
                // Hint: If you can't see the markers on the map if using the browser platform,
                //       try refreshing the page.
            };

            // Hint: We have provided the helper function nominatim.get which uses
            //       the OpenStreetMap REST API to turn an address into co-ordinates.
            //       It does this in such a way that requests are cached and sent to
            //       the OpenStreetMap REST API no more than once every 5 seconds.
            nominatim.get(address, onSuccess);
        }
    }

    // Clear any markers added to the map (if added to the markers array)
    function clearMarkersFromMap() {
        // This is implemented for you and no further work is needed on it
        markers.forEach(function (marker) {
            if (marker) {
                map.removeObject(marker);
            }
        });
        markers = [];
    }

    // Obtain the device location and centre the map
    function centreMap() {
        // This is implemented for you and no further work is needed on it

        function onSuccess(position) {
            console.log("Obtained position", position);
            var point = {
                //lng: position.coords.longitude,
                //lat: position.coords.latitude,
                //Hard-coding for testing - I used a virtual android device whose position was listed in Silicon Valley, USA.
                lng: '-0.040090',
                lat: '50.804810',
            };
            map.setCenter(point);
        }

        function onError(error) {
            console.error("Error calling getCurrentPosition", error);

            // Inform the user that an error occurred
            alert("Error obtaining location, please try again.");
        }

        // Note: This can take some time to callback or may never callback,
        //       if permissions are not set correctly on the phone/emulator/browser
        navigator.geolocation.getCurrentPosition(onSuccess, onError, {
            enableHighAccuracy: true,
        });
    }

    // TODO Update the map with addresses for orders from the Client Order API
    function updateMap() {
        // TODO adjust the following to get the required data from your HTML view
        var oucu = document.getElementById("oucu").value;
        SpinnerPlugin.activityStart("Looking for matches...");
        // TODO 2(a) FR2.1
        // You need to implement this function
        // See the TMA for an explanation of the functional requirements
        function onSuccess(obj) {
            console.log("match: received obj", obj);
            // Inform the user what happened
            alert("Matches for OUCU " + oucu + " have been found.");
            if (obj.status == "success") {
                var matches = obj.data;

                matches.forEach(function (match) {
                    if (match.offer_address == 'open university') {
                        addMarkerToMap('Open University, Milton Keynes');
                    }
                    else {
                        addMarkerToMap('Milton Keynes Central Station');
                    } 
                });
                
                centreMap();
                
            } else if (obj.message) {
                alert(obj.message);
                clearMarkersFromMap();
            } else {
                alert("No matches for " + oucu);
                clearMarkersFromMap();
            }
            SpinnerPlugin.activityStop();
        }
        // Post the OUCU to register with the Client Order API
        var url = url + "matches?OUCU=" + oucu;
        console.log("match: sending GET to " + url);
        $.ajax(url, { type: "GET", data: {}, success: onSuccess });
        // Hint: You will need to call addMarkerToMap and clearMarkersFromMap.
        // Hint: If you cannot complete FR2.1, call addMarkerToMap with a fixed value
        //       to allow yourself to move on to FR2.2.
        //       e.g. addMarkerToMap("Milton Keynes Central");
    }

    var clientInfo = [];
    var clientAddr = "";
    var clientLat = "";
    var clientLon = "";

    function getClientAddress(oucu, clientid) {

        function onListSuccess(obj) {
            console.log("Received client object ", obj); 
            var address = obj.data[0].address;
            console.log("Client Address: " + address);
            clientAddr = address;
        }

        // Get client info:
        var clientUrl = url + "clients/" + clientid + "?OUCU=sl23479&password=B1dSM4I4" ;
        console.log("user: " + oucu + " sending GET to " + clientUrl + "for client ID " + clientid);
        $.ajax(clientUrl, { type: "GET", data: {}, success: onListSuccess });
    }

    function getClientLocation(clientAddr) {

        function onListSuccess(obj) {
            console.log("GET Client Info: received obj", obj);
            clientLat =  obj.data[0].lat;
            clientLon =  obj.data[0].lon;    
            console.log("GET Client Info: Location identified", clientLat, clientLon);   
        }

        // Get client Lat/Lon:
        var geoUrl = "http://nominatim.openstreetmap.org/search/" + clientAddr + "?format=json&countrycodes=gb" ;
        console.log("Sending GET to Geo Service");
        $.ajax(geoUrl, { type: "GET", data: {}, success: onListSuccess });

    }

    //Initialise orders array 
    var oArray = [];
    function getOrders() {

        function onListSuccess(obj) {
            console.log("Got Orders Array: received obj", obj);
            oArray = obj.data;
        }

        // Get all the orders relating to a salesperson:
        var oUrl = url + "orders?OUCU=sl23479&password=B1dSM4I4" ;

        console.log("orders: Sending GET to " + oUrl);
        $.ajax(oUrl, { type: "GET", data: {}, success: onListSuccess });
        return onListSuccess;
    }

    // Populate orders array
    getOrders();

    //Initialise widget array    
    var wArray = [];
    
    function getWidgets() {

        function onListSuccess(obj) {
            console.log("Got Widgets Array: received obj", obj);
            wArray = obj.data;
        }

        // Get all the widgets
        var wUrl = url + "widgets?OUCU=sl23479&password=B1dSM4I4" ;

        console.log("widgets: Sending GET to " + wUrl);
        $.ajax(wUrl, { type: "GET", data: {}, success: onListSuccess });
        return onListSuccess;
    }

    // Populate widget array
    getWidgets();
    var wPos = 0;

    // Set widget array position, iterate across array and display widgets
    var index = function displayWidget (wPos) {
        return {
            prevWidget: function () {
                wPos && wPos--;
                console.log("prev: " + wPos);
                var img = document.createElement("img");
                img.src = wArray[wPos].url;
                img.setAttribute("style", "width: 50%;");
                var imgDiv = document.getElementById("widget-img");
                var existingImg = imgDiv.firstChild;
                if(existingImg){
                    imgDiv.replaceChild(img, existingImg);
                }
                else{
                    imgDiv.appendChild(img);
                }   
                imgDiv.setAttribute("style", "text-align:left");
    
                var desc = document.getElementById("widget-desc");
                desc.textContent = "Description: " + wArray[wPos].description;
                
                var price = document.getElementById("widget-price");
                price.textContent = "Price: " + wArray[wPos].pence_price / 100;
                wPos = wPos;
            },
            nextWidget: function () {
                wPos + 1 < wArray.length && wPos++;
                console.log("next: " + wPos);
                var img = document.createElement("img");
                img.src = wArray[wPos].url;
                img.setAttribute("style", "width: 50%;");
                var imgDiv = document.getElementById("widget-img");
                var existingImg = imgDiv.firstChild;
                if(existingImg){
                    imgDiv.replaceChild(img, existingImg);
                }
                else{
                    imgDiv.appendChild(img);
                }   
                imgDiv.setAttribute("style", "text-align:left");
    
                var desc = document.getElementById("widget-desc");
                desc.textContent = "Description: " + wArray[wPos].description;
                
                var price = document.getElementById("widget-price");
                price.textContent = "Price: " + wArray[wPos].pence_price / 100;
                wPos = wPos;
            }
        }    
    }(0);

    
    document.getElementById('btn-next').addEventListener('click', index.nextWidget);
    document.getElementById('btn-prev').addEventListener('click', index.prevWidget);

    // Set initial HERE Map position
    centreMap();

    // PUBLIC FUNCTIONS - available to the view
    // Note these are declared as this.functionName = function () { ... };

    this.centreMap = function () {
        // 2(a) FR3
        // This is implemented for you and no further work is needed on it
        // Update map now
        centreMap();
    };

    // Controller function to update map with matches to request or offer
    this.updateMap = function () {
        // 2(a) FR3
        // This is implemented for you and no further work is needed on it
        // Update map now
        updateMap();
    };

    this.nextWidget = function () {
        nextWidget();
    };

    this.beginOrder = function () {
        //var clientid = document.getElementById("clientid").value;
        var oucu = document.getElementById("oucu").value;
        if (oucu.match(/[a-z].*[0-9]$/)) {
            console.log("OUCU match found");
        }
        else {
            console.log("OUCU match not found");
        }

        
        console.log("Orders: " + oArray);

        //getClientLocation(clientAddr);
        //console.log("client location: " + clientLat + " " + clientLon + " received");
    };

    //this.addToOrder = function () {
    //    var subTotal = wArray[wPos].pence_price * document.getElementById("number");
    //};
  
}