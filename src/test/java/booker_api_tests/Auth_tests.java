package booker_api_tests;

import booker_api.SetUp;
import io.qameta.allure.Description;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static booker_api.Helpers.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class Auth_tests extends SetUp {

    @Test
    @Description("This test verifies that it's possible to create the Authentication Token with valid Credentials " +
            "and this Token allows to access the application.")
    @DisplayName("Create Token with valid Credentials")
    public void createToken_code200_test() {
        String newToken = step("Create the new token with a valid credentials",
                () -> getToken("admin", "password123")
        );

        step("Verify that it's possible to access to the GetBooking EP with the created token",
                () -> {
                    given()
                            .contentType("application/json")
                            .header("token", newToken)
                            .log()
                            .ifValidationFails(LogDetail.ALL)
                    .when()
                            .get("https://restful-booker.herokuapp.com/booking")
                    .then()
                            .log()
                            .ifValidationFails(LogDetail.ALL)
                    .assertThat()
                            .statusCode(200);
                }
        );
    }


    @Test
    @Description("This test verifies that on attempt to create a new Authentication Token with invalid Credentials " +
            "the proper error message in the Response will be shown.")
    @DisplayName("Create Token with invalid Credentials")
    public void createToken_invalidCreds_test() {
        step("Try to create the new token with an invalid credentials and verify that the response " +
                "states 'Bad credentials'", () -> {
            given()
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                            "    \"username\" : \"invalidUser\",\n" +
                            "    \"password\" : \"invalidPassword\"\n" +
                            "}")
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
                    .body("reason", equalTo("Bad credentials"));
            }
        );
    }

}
