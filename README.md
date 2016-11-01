# High Level Brainstorming

## User Stories
1. As the Control Center Operator (CCO), I would like to view a map of the Prison
2. As the CCO, I would like to view the location of all of the CO's on the map
3. As the CCO, I would like to view the history of all of the CO's previous locations on the map
4. As the CCO, I would like to quickly find a particular CO on the map
5. As the CCO, I would like to be notified of the reports that a CO sends
6. As the CCO, I would like to view the history of the reports that any CO has sent
7. As the Corrections Officer (CO), I would like to report my location to the control center
8. As the CO, I would like to report fire emergencies to the control center
9. As the CO, I would like to report miscellaneous emergencies to the control center

## Assumptions
* CCO would be using a Web Application UI being serviced by a Web Server that is accessible only from the location's LAN.
* COs would be using an Android Phone Application on a dedicated Android device that has no SIM card.

### As the CCO, I would like to view a map of the Prison
Options:
A. Indoor Google Maps
Google has added a great deal of support for indoor facilities. If the floor plan of the Prisons are public knowledge, submitting the floor plans to Google Maps would allow the application to leverage Google Maps' extensive location framework.

Pros:
Support from Google
Easy API and Built-In features for Web Applications
Nice UI - Can overlay custom icons for COs or other objects
Outdoor and Indoor maps
Built-In support for multiple floors

Cons:
Publicly available floor plans
Floor plan must be submitted to google and approved
Google Maps API Key comes at a minor cost

B. JPEG/PNG/Image of the Location
Simply put, a custom image of the location is used to display the location.

Pros:
Floor plans aren't available publicly
Cost is essentially free

Cons:
Quality of map UI is dependent on quality of image
Indoor and Outdoor location may be difficult
Would need to "reinvent the wheel" for displaying floor plan
Multiple floors would be simple but tedious

### As the CCO, I would like to view the location of all of the CO's on the map
Indoor tracking of COs will be covered at a later time. This User Story assumes that location data of COs are being sent from CO Android application to the Web Server. Also assuming that the map of the Prison exists, COs will appear as overlay icons on top of the map (Whether Google Maps or a simple image). Locations will be stored in a database on the Web Server as they are received. At some configurable update rate, the Web Application will request the latest position of all COs from the Web Server. The Web Server will respond with the latest locations of each. As the Web Application receives the latest positions, the Web Application will update the location of the CO overlay icons accordingly.

### As the CCO, I would like to view the history of all of the CO's previous locations on the map
The implementation details of this are unimportant at this time. The database of CO positions will store data for all of the known locations of each CO for as long as is required. At the start-up of the Web Application, the Web Application may request the known locations of each CO and display them as dots/lines/etc to indicate snail trail (aka bread crumb trail, etc.)

### As the CCO, I would like to quickly find a particular CO on the map
Some method of selection of a particular CO will need to be present in the Web Application. Searching by name, by Identification Number, assigned area?, etc. After searching for a CO, a selection should be allowed that will immediately move the camera of the Location Map to the position of the CO. 

### As the CCO, I would like to be notified of the reports that a CO sends
The sending of reports will be handled at a later time. This User Story assumes that reports are being sent from the CO application to the Web Server and stored on a database on the Web Server. When the Web Server receives a report from a CO, a push notification will be sent to the Web Application inidicating that a report was received by a particular CO.
Potential Options:
A. A non-modal pop-up dialog box that describes the report
"Non-Modal" means that the user is not restricted to only interacting with the pop-up - the CCO may choose to ignore the pop-up if it is a simple Location Update or there is a more concerning report that is being monitored. On the pop-up, the option of immediately selecting the CO and moving the camera to his position should be present.
B. Immediate selection of the CO that sent the report
As soon as a CO sends a report, the Web Application will "select" that CO and move the camera to their position.

### As the CCO, I would like to view the history of the reports that any CO has sent
This User Story assumes that CO reports (spanning some amount of time) are present in the database. Previously in this document, the idea of selecting a CO from the CCO Web Application has been mentioned (Selection of CO's should probably be added to the list of User Stories). Assuming that the action of "selecting" a CO exists - upon selection, the Web Application will request a list of the reports that a CO has sent (spanning some amount of time) from the Web Server and, upon receipt of the response, should be displayed to the CCO. Selection would include any event that focuses on the CO (Clicking a pop-up indicating a report has been received, searching and selecting a CO, etc.).

### As the Corrections Officer (CO), I would like to report my location to the control center
#### Options for Indoor Tracking:
##### Posyx
Kickstarter company that has recently reached its quota. Tracking tags are all Arduino based. Tracking is done over Ultra-Wide Band (UWB) which is classified as radio transmissions with bandwidth of over 500MHz.
Pros:
Cheaper solution

Cons:
Outdoor tracking would have to be implemented separately and by the development team of this project
Arduino based system means that an interface to the Arduino to the cell phone will have to be implemented (Bluetooth, etc.)

##### TRX
Small Company that works with Government Contractors.
##### Proprietary Solution

### As the CO, I would like to report fire emergencies to the control center
This is not explored at depth because it is presumed to be trivial. The Android Application will send a message to the Web Server indicating that a fire emergency is currently ongoing. The Web Server would then push that data to the Web Application but that sequence is not explored in this User Story.

### As the CO, I would like to report miscellaneous emergencies to the control center
Just like the above, this is not explored in depth as it is presumed to be the easiest of the Use-Cases. When development begins on this, an in-depth solution will be designed.
