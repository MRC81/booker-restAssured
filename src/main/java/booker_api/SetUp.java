package booker_api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;


import static booker_api.Helpers.isServiceAvailable;
import static io.restassured.RestAssured.*;


public class SetUp {
    //String token = getToken("admin", "password123");

    @BeforeAll
    @Step("Setup base Config and Request Spec")
    public static void setUp() {
        // this will skip the tests execution if the service is not available
        Assumptions.assumeTrue(isServiceAvailable(), "The service is not available! Skip all the tests");

        baseURI = "https://restful-booker.herokuapp.com";
        //basePath = "/booking/";
        //RestAssured.port = 8080;
        config = config()
                .encoderConfig(EncoderConfig
                        .encoderConfig()
                        .defaultContentCharset("UTF-8"));

        requestSpecification = new RequestSpecBuilder()
                //.addHeader("token", token)
                .addHeader("Authorization", " Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .build()
                .filter(new AllureRestAssured()
                        .setRequestTemplate("http-request.ftl")
                        .setResponseTemplate("http-response.ftl")
                );
    }
}
