/**
 * TM352-20J Block 3 Part 2 Simple App example
 * Created by Stephen Rice: 10 Nov 2020
 */

// Execute in strict mode to prevent some common mistakes
"use strict";

// Create a SimpleApp object for use by the HTML view
var controller = new SimpleApp();

// JavaScript "class" containing the model, providing controller "methods" for the HTML view
function SimpleApp() {
    console.log("Creating controller/model");

    // PRIVATE FUNCTIONS - available only to code inside the controller/model
    // Note these are declared as function functionName() { ... }

    // Find coordinates for a given place and update the view
    function findCoords(place) {
        var url =
            "http://nominatim.openstreetmap.org/search/" +
            encodeURIComponent(place) +
            "?format=json&countrycodes=gb";

        function onSuccess(data) {
            console.log("Received data from " + url, data);

            // When the data is returned, build a message using the data
            var message = "no coordinates found!";
            if (data.length > 0) {
                // Read the first set of coordinates from the data
                var lat = data[0].lat;
                var lon = data[0].lon;
                message = "latitude= " + lat + "; longitude= " + lon;
            }

            // Update the <span> element in the view by its ID attribute
            $("#reply").text(message);
            // Or do this using "vanilla" JavaScript
            // document.getElementById("reply").innerText = message;
        }

        // Call the OpenStreetMap REST API
        console.log("Sending GET to " + url);
        $.ajax(url, { type: "GET", data: {}, success: onSuccess });
        // Or do this using "vanilla" JavaScript
        // fetch(url, { method: "GET" }).then(function (response) { response.json().then(onSuccess); });
    }

    // PUBLIC FUNCTIONS -  available to the view
    // Note these are declared as this.functionName = function () { ... };

    // Controller function for Find coordinates button
    // Call this by adding onclick="controller.coords()" to a HTML button
    this.coords = function () {
        console.log("Find coordinates button pressed");

        // Read the place name from the <input> element by its ID attribute
        var place = $("#place").val();
        // Or do this using "vanilla" JavaScript
        // var place = document.getElementById("place").value;

        // Call a private function in the model to find data and update the view
        findCoords(place);
    };

    // Controller function for Yes button
    // Call this by adding onclick="controller.coords()" to a HTML button
    this.yes = function () {
        console.log("Yes button pressed");
        alert("Goody!");
    };

    // Controller function for No button
    // Call this by adding onclick="controller.coords()" to a HTML button
    this.no = function () {
        console.log("No button pressed");
        alert("Oops!");
    };
}
