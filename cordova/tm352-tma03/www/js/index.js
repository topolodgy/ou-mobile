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

// Declare a TaxiShare object for use by the HTML view
var controller;
document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
    console.log("Running cordova-" + cordova.platformId + "@" + cordova.version);
    // Create the TaxiShare object for use by the HTML view
    controller = new TaxiShare();
}

// JavaScript "class" containing the model, providing controller "methods" for the HTML view
function TaxiShare() {
    console.log("Creating controller/model");

    // PRIVATE VARIABLES AND FUNCTIONS - available only to code inside the controller/model
    // Note these are declared as function functionName() { ... }

    var BASE_GET_URL = "http://137.108.92.9/openstack/taxi/";
	// NOTE HTTPS: at the time of writting we are investigating the creating a https service
	//             this will increase compatibility with Android. If we get it working we 
    //             will ask you to modify the line above.	
    var BASE_URL = BASE_GET_URL;
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
                lng: '-0.75583',
                lat: '52.04172',
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

    // TODO Update the map with addresses for orders from the Taxi Sharing API
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
        // Post the OUCU to register with the Taxi Sharing API
        var url = BASE_URL + "matches?OUCU=" + oucu;
        console.log("match: sending GET to " + url);
        $.ajax(url, { type: "GET", data: {}, success: onSuccess });
        // Hint: You will need to call addMarkerToMap and clearMarkersFromMap.
        // Hint: If you cannot complete FR2.1, call addMarkerToMap with a fixed value
        //       to allow yourself to move on to FR2.2.
        //       e.g. addMarkerToMap("Milton Keynes Central");
    }

    // Register OUCU with the taxi sharing service
    function register(oucu) {
        // 2(a) FR3 - done
        // This is implemented for you and no further work is needed on it
        // Note we have pre-registered your OUCU so using this is only required
        // should you want to add an additional (fictional) OUCU for testing.
        function onSuccess(obj) {
            
            console.log("register: received obj", obj);

            // Inform the user what happened
            if (obj.status == "success") {
                alert("User " + oucu + " has been successfully registered.");
            } else if (obj.message) {
                alert(obj.message);
            } else {
                alert("Invalid OUCU: " + oucu);
            }
        }
        // Post the OUCU to register with the Taxi Sharing API
        var url = BASE_URL + "users";
        console.log("register: sending POST to " + url);
        $.ajax(url, { type: "POST", data: { oucu: oucu }, success: onSuccess });
    }

    function offer(oucu, address, startTime, endTime) {
        function onSuccess(obj) {
            
            console.log("offer: received obj", obj);

            // Inform the user what happened
            if (obj.status == "success") {
                alert("Offer has been successfully registered.");
            } else if (obj.message) {
                alert(obj.message);
            } else {
                alert("Invalid OUCU: " + oucu);
            }
        }
        var url = BASE_URL + "orders";
        console.log("offer: sending POST to " + url);
        $.ajax(url, { type: "POST", data: { oucu: oucu, type: 0, address: address, start: startTime, end:endTime }, success: onSuccess });
    }

    // TODO Request an offered taxi for the given OUCU
    function request(oucu, address, startTime) {
        // TODO 2(a) FR1.2
        // You need to implement this function
        // See the TMA for an explanation of the functional requirements
        function onSuccess(obj) {
            
            console.log("offer: received obj", obj);

            // Inform the user what happened
            if (obj.status == "success") {
                alert("Request has been successfully registered.");
            } else if (obj.message) {
                alert(obj.message);
            } else {
                alert("Invalid OUCU: " + oucu);
            }
        }
        var url = BASE_URL + "orders";
        console.log("offer: sending POST to " + url);
        $.ajax(url, { type: "POST", data: { oucu: oucu, type: 1, address: address, start: startTime }, success: onSuccess });
    }

    // Cancel all orders (offers and requests) for the given OUCU
    function cancel(oucu) {
        // 2(a) FR1.3
        // This is implemented for you and no further work is needed on it

        function onDeleteSuccess(obj) {
            
            console.log("cancel/delete: received obj", obj);
        }

        function onListSuccess(obj) {
             
            console.log("cancel/list: received obj", obj);

            if (obj.status == "success") {
                // Orders are in an array named "data" inside the "obj" object
                var orders = obj.data;

                // Inform the user what is happening
                alert("Deleting " + orders.length + " orders");

                // Loop through each one and delete it
                orders.forEach(function (order) {
                    // Delete the order with this ID for the given OUCU
                    var deleteUrl = BASE_URL + "orders/" + order.id + "?oucu=" + oucu;
                    console.log("cancel/delete: Sending DELETE to " + deleteUrl);
                    $.ajax(deleteUrl, {
                        type: "DELETE",
                        data: {},
                        success: onDeleteSuccess,
                    });
                });
            } else if (obj.message) {
                alert(obj.message);
            } else {
                alert(obj.status + " " + obj.data[0].reason);
            }
        }

        // Get all the orders (offers and requests) for the given OUCU
        var listUrl = BASE_GET_URL + "orders?oucu=" + oucu;
        console.log("cancel/list: Sending GET to " + listUrl);
        $.ajax(listUrl, { type: "GET", data: {}, success: onListSuccess });
    }

    // Set initial HERE Map position
    centreMap();

    // PUBLIC FUNCTIONS - available to the view
    // Note these are declared as this.functionName = function () { ... };

    // Controller function to update map with matches to request or offer
    this.updateMap = function () {
        // 2(a) FR3
        // This is implemented for you and no further work is needed on it
        // Update map now
        updateMap();
    };

    // Controller function to register a user with the web service
    this.registerUser = function () {
        // 2(a) FR3
        var oucu = document.getElementById("oucu").value;
        console.log("registerUser: oucu = " + oucu);
        register(oucu);
    };

    // Controller function for user to offer or request a taxi share
    this.shareTaxi = function () {

        var shareType = document.getElementById("share-type");
        var option = shareType.options[shareType.selectedIndex].text;
        var oucu = document.getElementById("oucu").value;
        var address = document.getElementById("address").value;
        var startTime = document.getElementById("start-time").value; // eg. 2020:12:18:14:38
        var hours = document.getElementById("wait-time").value; // duration in hours

        if (option == "Offer") {
            // The model requires an end time, but the view provides a duration, so...
            // ...convert the start time back to a Date object...
            var endDate = convertFromOrderTime(startTime);
            // ...add on the hours (ensuring the string is an integer first)...
            endDate.setHours(endDate.getHours() + parseInt(hours));
            // ...convert back to an end time string
            var endTime = convertToOrderTime(endDate);
            // Call the model using values from the view
            offer(oucu, address, startTime, endTime);
            console.log("Share offer received");
        }
        else {
            request(oucu, address, startTime);
            console.log("Share request received");
        }
    };

    // Function no longer required - implemented under shareTaxi function (above).
    //this.requestTaxi = function () {
        // TODO adjust the following to get the required data from your HTML view
        // Call the model using values from the view
        //request(oucu, address, startTime);
    //};

    // Controller function for user to cancel all their offers and requests
    this.cancel = function () {
        // TODO adjust the following to get the required data from your HTML view
        var oucu = document.getElementById("oucu").value;
        // Call the model using values from the view
        cancel(oucu);
    };
}