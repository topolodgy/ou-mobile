/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

// Wait for the deviceready event before using any of Cordova's device APIs.
// See https://cordova.apache.org/docs/en/latest/cordova/events/events.html#deviceready
// Execute in strict mode to prevent some common mistakes "use strict";

// Declare a Stopwatch object for use by the HTML view var controller;

document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
    console.log("Running cordova-" + cordova.platformId + "@" + cordova.version);
    console.log("Camera object:", navigator.camera);
    // Create the Stopwatch object for use by the HTML view
 controller = new Stopwatch();
}

// JavaScript "class" containing the model, providing controller "methods" for the HTML view
function Stopwatch() {
    console.log("Creating controller/model");
    // PRIVATE VARIABLES AND FUNCTIONS - available only to code inside the controller/model
    // Note these are declared as function functionName() { ... }
    // Return the number of milliseconds since the UNIX epoch
    function getTime() {
      return new Date().getTime();
    }

    // Convert a time in milliseconds to a string minutes:seconds.hundredths
    function formatTime(milliseconds) {
      var seconds = ((milliseconds / 1000) % 60).toFixed(2);
      var minutes = Math.floor(milliseconds / 1000 / 60);
      return ("0" + minutes).substr(-2) + ":" + ("0" + seconds).substr(-5);
    }

    // Stopwatch state
    var timerInterval;
    var startTime;

    // Calculate elapsed time and display as minutes:seconds.hundredths
    function updateTimer() {
      var milliseconds = getTime() - startTime;
      document.getElementById("stopwatch").innerHTML = formatTime(milliseconds);
    }

    var icon = new H.map.DomIcon("<div>&#x1F3C3;</div>");
    var mapInterval;
    var destination;
    var marker;
    var bubble;

    function updateMap() {
        function onSuccess(position) {
            console.log("Obtained position", position);
            
            var point = {
                lng: position.coords.longitude,
                lat: position.coords.latitude,
            };
            if (marker) {
                // Remove marker if it already exists
                map.removeObject(marker);
            }
            if (bubble) {
                // Remove bubble if it already exists
                ui.removeBubble(bubble);
            }
                map.setCenter(point);
            marker = new H.map.DomMarker(point, { icon: icon });
            map.addObject(marker);
            // Set destination to position of first marker
            if (destination) {
                bubble = new H.ui.InfoBubble(destination, {
                    content: "<b>You want to get there!</b>",
                });
                ui.addBubble(bubble);
            }
        }
        function onError (error) {
            console.error("Error calling getCurrentPosition", error);
        }
        navigator.geolocation.getCurrentPosition(onSuccess, onError, {
            enableHighAccuracy: true,
        });
    }

    // Update map on startup
    updateMap();

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
            zoom: 14,
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

    // Add an event listener to set intended destination
    map.addEventListener("tap", function (evt) {
        // Update destination
        destination = map.screenToGeo(
            evt.currentPointer.viewportX,
            evt.currentPointer.viewportY
        );
        // Update map now
        updateMap();
    });

    // PUBLIC FUNCTIONS - available to the view
    // Note these are declared as this.functionName = function () { ... };
    // Controller function for Start button
    this.start = function () {
        // Press Stop to clear existing intervals
        this.stop();
        // Reset display
        startTime = getTime();
        console.log("Starting timer");
        document.getElementById("stopwatch").innerText = "00:00:00";
        // Start new intervals
        timerInterval = setInterval(updateTimer, 100);
        mapInterval = setInterval(updateMap, 10000);
    };

    // Controller function for Stop button
    this.stop = function () {
        console.log("Stopping timer");
        clearInterval(timerInterval);
        clearInterval(mapInterval);
    };

    // Controller function for Photo button
    this.photo = function () {
        function onSuccess(data) {
            console.log("Obtained picture data");
            if (cordova.platformId === "browser") {
                // Set source of image on the page (plugin returns base64 data on browser platform)
                document.getElementById("image").src = "data:image/jpeg;base64," + data;
            } else {
                // Set source of image on the page
                document.getElementById("image").src = data;
            }
            // Show the image on the page
            document.getElementById("image").style.display = "block";
        }
        function onError(error) {
            console.error("Error calling getPicture", error);
        }
        // Hide the image on the page
        document.getElementById("image").style.display = "none";
        // Call the cordova-plugin-camera API
        navigator.camera.getPicture(onSuccess, onError);
    };
}