/***********
 * OU TM352 Block 3, EMA: index.js
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

    function addOrderMarkers(lat, lon) {   
            var point = {
                lat: lat,
                lng: lon,
            };             
            marker = new H.map.DomMarker(point, { icon: icon });
            map.addObject(marker);
            console.log('Marker added');
            markers.push(marker);
        };


    // Obtain the device location and centre the map
    //FR2.1 - Display current salesperson's location
    function centreMap() {

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

        navigator.geolocation.getCurrentPosition(onSuccess, onError, {
            enableHighAccuracy: true,
        });
    }

    centreMap();


    // This section I was unable to complete successfully, and so had to resort to pinning the order_id variable used in my beginOrder function to a hardcoded value. 
    // I also had to do the same with the initial location of the order, in order to progress the FR for order placing.

    // function getClientAddress(clientid, pass, oucu) {

       // function onListSuccess(obj) {
        //     console.log("Received client object ", obj); 
        //     newClientAddr = obj.data[0].address;
        //     console.log("newClientAddr var is " + newClientAddr);
        // }

        // Get client info:
        // var clientUrl = url + "clients/" + clientid + "?OUCU=" + oucu + "&password=" + pass;
        // console.log("user: " + oucu + " sending GET to " + clientUrl + "for client ID " + clientid);
        // $.ajax(clientUrl, { type: "GET", data: {}, success: onListSuccess });
        // return onListSuccess;
    // }

    // var clientLat = 0;
    // var clientLon = 0;

    // function getClientLocation() {

       //  function onSuccess(obj) {
          //   console.log("received address is " + newClientAddr);
          //   console.log("GET Client Info: received obj", obj);
          //   clientLat =  obj[0].lat;
          //   clientLon =  obj[0].lon;   
          //   console.log("GET Client Info: Location identified", obj);   
        // }

        // Get client Lat/Lon:
        // var geoUrl = "http://nominatim.openstreetmap.org/search/" + newClientAddr + "?format=json&countrycodes=gb";
        // console.log("Sending GET to Geo Service for address " + newClientAddr);
        // $.ajax(geoUrl, { type: "GET", data: {}, success: onSuccess });

        // return onSuccess;
    // }

    function beginOrder(clientid, pass, oucu) {

        //var addr = getClientAddress(oucu, clientid);

        //var location = getClientLocation(newClientAddr);
        //console.log("createOrder: Client Location: " + newClientAddr);

        getOrders(pass, oucu);
        getWidgets(pass, oucu);

        function onSuccess(obj) {
            console.log("Order created!", obj);
        }

        var oUrl = url + "orders?OUCU=" + oucu + "&password=" + pass;

        console.log("orders: Sending POST to " + oUrl);
        $.ajax(oUrl, { type: "POST", data: {client_id: clientid, latitude: 0, longitude: 0, OUCU: oucu, password: pass}, success: onSuccess });
        return onSuccess;
    }

        // FR1.3 - Add widget to order
        function addToOrder(oucu, pass, number, price) {

            //FR1.4 - Display VAT, totals
            var subtotal = number * price;
            console.log("addToOrder: subtotal is " + subtotal);
            document.getElementById("subtotal").innerHTML = subtotal + " GBP";
    
            var vat = subtotal * 0.2;
            console.log("addToOrder: vat is " + vat);
            document.getElementById("vat").innerHTML = vat + " GBP";
    
            var total = subtotal + vat;
            console.log("addToOrder: total is " + total);
            document.getElementById("total").innerHTML = total + " GBP";
    
            var widgetid = wArray[wPos].id;
            console.log("Widget ID = " + widgetid);
    
            function onSuccess(obj) {
                console.log("Order amended!", obj);
            }
    
            var oUrl = url + "order_items";
            console.log("Order addition: Sending POST to " + oUrl);
            //FR1.5 Save order
            $.ajax(oUrl, { type: "POST", data: {OUCU: oucu, password: pass, order_id: "541271537", widget_id: widgetid, number: number, pence_price: price}, success: onSuccess });
            return onSuccess;
        }


    //Initialise orders array 
    var oArray = [];

    function getOrders(pass, oucu) {

        function onListSuccess(obj) {
            console.log("Got Orders Array: received obj", obj);
            oArray = obj.data;
        }

        // Get all the orders relating to a salesperson:
        var oUrl = url + "orders?OUCU=" + oucu + "&password=" + pass;

        console.log("orders: Sending GET to " + oUrl);
        $.ajax(oUrl, { type: "GET", data: {}, success: onListSuccess });
        return onListSuccess;
    }

    //Initialise widget array    
    var wArray = [];
    
    function getWidgets(pass, oucu) {

        function onListSuccess(obj) {
            console.log("Got Widgets Array: received obj", obj);
            wArray = obj.data;
        }

        // Get all the widgets
        var wUrl = url + "widgets?OUCU=" + oucu + "&password=" + pass;

        console.log("widgets: Sending GET to " + wUrl);
        $.ajax(wUrl, { type: "GET", data: {}, success: onListSuccess });
        return onListSuccess;
    }

    // Set widget array position
    var wPos = 0;

    // FR1.2 - Set widget array position, iterate across array and display widget descriptions, images, and prices.
    var index = function displayWidget (wPos) {
        return {
            prevWidget: function () {
                wPos && wPos--;
                console.log("Previous Widget: " + wPos);
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
                console.log("Next Widget: " + wPos);
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



    // PUBLIC FUNCTIONS - available to the view
    // Note these are declared as this.functionName = function () { ... };

    this.beginOrder = function () {
        var clientid = document.getElementById("clientid").value;
        var oucu = document.getElementById("oucu").value;
        var pass = document.getElementById("pass").value;

        //FR1.1 - Validate that the OUCU starts with a letter and ends with a number
        if (oucu.match(/[a-z].*[0-9]$/)) {
            console.log("OUCU match found");
        }
        else {
            console.log("OUCU match not found");
        }

        beginOrder(clientid, pass, oucu);

        //FR2.2 - Place markers for orders on the map
        for(let i = 0; i < oArray.length; i++) {
            var lat = oArray[i].latitude;
            var lon = oArray[i].longitude
            addOrderMarkers(lat, lon);
        }
    };
  

    //FR1.3 - Add a widget to the order
    this.addToOrder = function () {
        var oucu = document.getElementById("oucu").value;
        var pass = document.getElementById("pass").value;

        var number = document.getElementById("number").value;
        console.log("addToOrder: number is " + number);
        var price = document.getElementById("price").value;
        console.log("addToOrder: price is " + price);

        addToOrder(oucu, pass, number, price);

    };
}