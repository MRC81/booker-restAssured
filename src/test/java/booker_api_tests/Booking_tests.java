package booker_api_tests;

import booker_api.POJOs.BookingDates;
import booker_api.POJOs.Booking;
import booker_api.SetUp;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static booker_api.Helpers.*;
import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SkipWhenServiceIsUnavailable
public class Booking_tests extends SetUp {

    @Test
    @Description("This test verifies that getBooking EP returns code 200 if a valid id was provided " +
            "and the information about the booking record of the corresponding id.")
    @DisplayName("Verifies getBooking EP code 200 case")
    public void getBooking_code200_test() throws JsonProcessingException {
        Booking payloadPOJO = new Booking(
            "Bob",
            "Cowley",
            "",
            50,
            false,
            new BookingDates("2021-06-23", "2021-06-27")
        );
        step("New booking POJO", () ->
                attachment("Create a new booking POJO", payloadPOJO.BookingToString())
        );

        int newBookingId = createBooking(pojoToJson(payloadPOJO));
        step("The new booking ID is: " + newBookingId);

        step("Send a request with the id and verify that the response is correct",
                () -> {
            given()
                    .contentType("application/json")
                    .log()
                    .ifValidationFails(LogDetail.ALL)
            .when()
                    .get("booking/" + newBookingId)
            .then()
                    .log()
                    .ifValidationFails(LogDetail.ALL)
            .assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("firstname", equalTo("Bob"),
                            "lastname", equalTo("Cowley"),
                            "totalprice", equalTo(50),
                            "depositpaid", equalTo(false),
                            "bookingdates.checkin", equalTo("2021-06-23"),
                            "bookingdates.checkout", equalTo("2021-06-27"),
                            "additionalneeds", equalTo("")
                    );
            }
        );
    }



    @Test
    @Description("This test verifies that createBooking EP returns code 200 if a valid JSON input was provided " +
            "and creates a new booking record which data matches the input.")
    @DisplayName("Verifies createBooking EP code 200 case")
    public void createBooking_code200_test() {
        Booking payloadPOJO = new Booking(
                "John",
                "Smith",
                "Breakfast",
                100,
                true,
                new BookingDates("2021-04-01", "2021-04-10")
        );
        step("Create a new booking POJO", () ->
                attachment("New booking POJO", payloadPOJO.BookingToString())
        );

        int id = step("Send a request with the POJO and verify that the response is correct",
                () -> {
            Response response =
                    given()
                            .contentType(ContentType.JSON)
                            .body(payloadPOJO)
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
                            .body("bookingid", isA(Integer.class),
                                    "booking.firstname", equalTo("John"),
                                    "booking.lastname", equalTo("Smith"),
                                    "booking.totalprice", equalTo(100),
                                    "booking.depositpaid", equalTo(true),
                                    "booking.bookingdates.checkin", equalTo("2021-04-01"),
                                    "booking.bookingdates.checkout", equalTo("2021-04-10"),
                                    "booking.additionalneeds", equalTo("Breakfast")
                            )
                    .extract()
                    .response();
                    // extract the id of the booking
                    return response.path("bookingid");
            }
        );
        step("The ID of the new booking record is: " + id);

        List<Integer> listOfIds = getAllBookingIds();

        step("Verify that the ID is present in the list of all existing bookings", () ->
            assertTrue(listOfIds.contains(id), "The new booking id '" + id + "' is not present in " +
                    "the list of existing IDs: " + listOfIds )
        );
    }



    @Issue("EP returns '403 - Forbidden' with the auth token although requirements were met")
    @Test
    @Description("This test verifies that updateBooking EP returns code 200 if a valid id and a valid JSON update " +
            "data were provided and the update data was applied to the booking record of the corresponding id.")
    @DisplayName("Verifies updateBooking EP code 200 case")
    public void updateBooking_code200_test() throws JsonProcessingException {
        Booking payloadPOJO = new Booking(
                "John",
                "Doe",
                "Coffin",
                10,
                true,
                new BookingDates("2021-01-01", "2021-01-02")
        );
        step("Create a new booking POJO", () ->
                attachment("New booking POJO", payloadPOJO.BookingToString())
        );

        Booking updatePOJO = new Booking(
                "John",
                "MoveOn",
                "Coffee",
                0,
                false,
                new BookingDates("2021-01-02", "2021-01-03")
        );
        step("Create the update POJO for the new booking POJO", () ->
                attachment("Update POJO", updatePOJO.BookingToString())
        );

        int bookingId = createBooking(pojoToJson(payloadPOJO));
        step("The booking ID is: " + bookingId);

        step("Send a request with the booking iD and the update POJO and verify that the response shows " +
                        "the updated booking data", () -> {
            given()
                    .contentType(ContentType.JSON)
                    .body(updatePOJO)
                    .log()
                    .ifValidationFails(LogDetail.ALL)
            .when()
                    .put("booking/" + bookingId)
            .then()
                    .log()
                    .ifValidationFails(LogDetail.ALL)
            .assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(
                            "firstname", equalTo("John"),
                            "lastname", equalTo("MoveOn"),
                            "totalprice", equalTo(0),
                            "depositpaid", equalTo(false),
                            "bookingdates.checkin", equalTo("2021-01-02"),
                            "bookingdates.checkout", equalTo("2021-01-03"),
                            "additionalneeds", equalTo("Coffee")
                    );
            }
        );
    }



    @Issue("EP returns '403 - Forbidden' with the auth token although requirements were met")
    @Test
    @Description("This test verifies that partialUpdateBooking EP returns code 200 if a valid id and a valid JSON " +
            "partial update data were provided and the partial update data was applied to the booking record of " +
            "the corresponding id.")
    @DisplayName("Verifies partialUpdateBooking EP code 200 case")
    public void partialUpdateBooking_code200_test() throws JsonProcessingException {
        Booking payloadPOJO = new Booking(
                "Gandalf",
                "Gray",
                "Staff",
                1000,
                true,
                new BookingDates("2001-05-01", "2001-07-12")
        );
        step("Create a new booking POJO", () ->
                attachment("New booking POJO", payloadPOJO.BookingToString())
        );

        String partialUpdateJSON = "{\n" +
                "    \"firstname\" : \"Gandalf\",\n" +
                "    \"lastname\" : \"White\"\n" +
                "}";
        step("Create the partial update JSON", () ->
                attachment("partial update JSON", partialUpdateJSON)
        );

        int bookingId = createBooking(pojoToJson(payloadPOJO));
        step("The booking ID is: " + bookingId);

        step("Send a request with the booking ID and the partial update JSON and verify that the response shows " +
                "the updated booking data", () -> {
            given()
                    .contentType(ContentType.JSON)
                    .body(partialUpdateJSON)
                    .log()
                    .ifValidationFails(LogDetail.ALL)
            .when()
                    .patch("booking/" + bookingId)
            .then()
                    .log()
                    .ifValidationFails(LogDetail.ALL)
            .assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(
                            "firstname", equalTo("Gandalf"),
                            "lastname", equalTo("White"),
                            "totalprice", equalTo(1000),
                            "depositpaid", equalTo(true),
                            "bookingdates.checkin", equalTo("2001-05-01"),
                            "bookingdates.checkout", equalTo("2001-07-12"),
                            "additionalneeds", equalTo("Staff")
                    );
                }
        );


    }


    @Test
    @Description("This test verifies that deleteBooking EP deletes the booking record of the specified valid id.")
    @DisplayName("Verifies deleteBooking EP code 200 case")
    public void deleteBooking_code201_test() {
        List<Integer> listOfIds = getAllBookingIds();

        int bookingIdToDelete = step("Define which ID will be deleted", () -> {
            // if there are no booking ids yet create a new on to be deleted otherwise pick the 1st one from existed
            if (listOfIds.isEmpty()) {
                Booking payloadPOJO = new Booking(
                        "Billy",
                        "Bones",
                        "Rum",
                        0,
                        false,
                        new BookingDates("1765-06-23", "1765-06-27")
                );
                step("Create a new booking POJO", () ->
                        attachment("New booking POJO", payloadPOJO.BookingToString())
                );

                return createBooking(pojoToJson(payloadPOJO));
            } else {
                return listOfIds.get(0);
            }
        });

        deleteBooking(bookingIdToDelete);

        List<Integer> newListOfIds = getAllBookingIds();

        step("Verify that ID of the deleted record is not present in the list of existing booking IDs",
                () -> assertFalse(newListOfIds.contains(bookingIdToDelete), "The id '" + bookingIdToDelete +
                        "' which should have been deleted is present in the list of existing IDs: " + listOfIds));
    }


}
