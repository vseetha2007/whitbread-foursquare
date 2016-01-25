Foursquare Integration Service - proof of concept
=====================

##Summary

This application is an Venue Search application which integrates Foursquare API
The frameworks and libraries that were used are:
* **Spring Boot** - for fast bootstrapping of the web app
* **Gson** - for deserialization
* **BootStrap CSS Framework**- for UI
* **Apache HTTP Client** - for http calls
* **Apache Commons IO/Lang** - utility libraries


##Approach
The application has a main service which calls an HTTP client. The client supports a maximum
of 50 concurrent connections and has a 5 second timeout set. The response from the Foursquare
API is cached to avoid unnecessary trips. The cache is flushed every minute (for the sake of
the demo) in order to avoid memory issues. The response from Foursquare API is deserialized
using Gson library and then forwarded to the view component for rendering.


##Build and start the app
For bootstrapping the app you need Java 8 JDK and Maven 3.
Using Maven, build the application with the following command:
```
mvn clean install
```
and then run the application by typing
```
java -jar target/whitbread-foursquare-integration-1.0-SNAPSHOT.jar
```
Or checking out the code in your IDE and run it selecting
```
RunAs - Spring Boot App
```
##Using the application
After starting the application you can access it in the browser:
```
http://localhost:8080/explore
```


##Notes
Due to time constraints I did not write unit tests and have not handled validation or exception handling properly. It is not the best approach but
this was a tradeoff.


##Improvements Required

* Unit tests
* Validations of the user input
* Exception Handling
