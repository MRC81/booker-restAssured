# booker-restAssured

## About
Here is my attempt to write some autotest for the [Restful-booker sevice](https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking-GetBookings) with Gradle-JUnit5-RestAssured-Allure stack and a focus to Allure report look. 


## Tech Stack
* Java 11
* Gradle 6.8
* JUnit 5
* Rest Assured 4.3.3
* Allure 2.13


## How to run
Pre-requisites: to run the project you will need Java 11 or higher, Gradle installation is not necessary since the project utilized a Gradle wrapper.

To run the project just execute in command-line from the project's root folder:
```
gradlew clean test (on Windows)
```
or 
```
./gradlew clean test (on Linux)
```

## Test results

After tests execution will be completed, you can check the results with the *index.html* from the **/build/reports/tests/test** folder or run Allure report dashboard from the **/build** folder by executing the following command:
```
allure serve
```
but this will require [installing Allure](https://docs.qameta.io/allure/#_installing_a_commandline) on your system in the first place. You will also need to add the Allure to your $PATH environment variable to be able to call it from the **/build** folder.
