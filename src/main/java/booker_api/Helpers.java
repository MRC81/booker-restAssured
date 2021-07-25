package booker_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.util.List;

import static io.restassured.RestAssured.given;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;

public class Helpers {

    /** This method gets a new access token for the specified Username and Password
     *
     * @param username - the username value
     * @param password - the password value
     * @return - the token value
     */
    public static String getToken(String username, String password) {
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body("{\"username\" : \""+username+"\",\n" +
                                "\"password\" : \""+password+"\"}")
                        .log()
                        .ifValidationFails(LogDetail.ALL)
                .when()
                        .post("https://restful-booker.herokuapp.com/auth")
                .then()
                        .log()
                        .ifValidationFails(LogDetail.ALL)
                .assertThat()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("token", Matchers.notNullValue())
                        .extract()
                        .response();

        return response.path("token");
    }


    /** This method returns all existed booking ids
     *
     * @return List<Object> with existed booing ids
     */
    @Step("Get the list of all existing booking IDs")
    public static List<Integer> getAllBookingIds() {
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .log()
                        .ifValidationFails(LogDetail.ALL)
                .when()
                        .get("https://restful-booker.herokuapp.com/booking")
                .then()
                        .log()
                        .ifValidationFails(LogDetail.ALL)
                .assertThat()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();

        return response.jsonPath().getList("bookingid", Integer.class);
    }

    /** This method creates a booking record with the specified input data via appropriate entry point
     *  and return the id of the created record.
     *
     * @param jsonBookingInfo - a string with valid JSON format data necessary to create a new booking record.
     *                       It will be validated against the booking_json_schema.json schema.
     * @return the id of the created record.
     */
    @Step("Create a new booking record")
    public static int createBooking(String jsonBookingInfo) {
        // check that jsonBookingInfo is a valid JSON according to the booking_json_schema.json schema
        assertThat(jsonBookingInfo, matchesJsonSchemaInClasspath("booking_json_schema.json"));

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(jsonBookingInfo)
                        .log()
                        .ifValidationFails(LogDetail.ALL)
                .when()
                        .post("booking")
                .then()
                        .log()
                        .ifValidationFails(LogDetail.ALL)
                .assertThat()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("bookingid", isA(Integer.class))
                .extract()
                .response();

        return response.path("bookingid");
    }


    /** This method converts the specified POJO into JSON string.
     *
     * @param pojo - input POJO argument
     * @return - String with JSON from the POJO
     * @throws JsonProcessingException in case of problems with JSON processing
     */
    public static String pojoToJson(Object pojo) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(pojo);
    }


    /** This method deletes the booking of the specified id
     *
     * @param id - the id of the booking to delete
     */
    @Step("Delete booking record with the ID {0}")
    public static void deleteBooking(int id) {
        // set parser to work with 'text/plain'
        RestAssured.registerParser("text/plain", Parser.TEXT);
        // send request with the id and verify that the response is correct
        given()
                .contentType(ContentType.TEXT)
                .log()
                .ifValidationFails(LogDetail.ALL)
        .when()
                .delete("booking/" + id)
        .then()
                .log()
                .ifValidationFails(LogDetail.ALL)
        .assertThat()
                .statusCode(201)
                .contentType(ContentType.TEXT)
                .body(equalTo("Created")); // I assume it should be 'Deleted' but it returns 'Created'
    }


    /** This method verifies that the restful-booker.herokuapp.com service is available via the Ping entry point
     *
     * @return - true if EP returns code 201 and the body 'Created' otherwise - false
     */
    @Step("Check the Service availability")
    public static boolean isServiceAvailable() {
        // set parser to work with 'text/plain'
        RestAssured.registerParser("text/plain", Parser.TEXT);

        Response response =
                given().header("Authorization", " Basic YWRtaW46cGFzc3dvcmQxMjM=")
                    .contentType(ContentType.TEXT)
                    .log()
                    .ifValidationFails(LogDetail.ALL)
                .when()
                    .get("https://restful-booker.herokuapp.com/ping")
                .then()
                    .log()
                    .ifValidationFails(LogDetail.ALL)
               .extract().response();

        return response.statusCode() == 201
                && response.body().prettyPrint().equals("Created");
    }


}
