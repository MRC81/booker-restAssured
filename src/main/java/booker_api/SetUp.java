package booker_api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import org.junit.jupiter.api.BeforeEach;

import static booker_api.Helpers.getToken;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;


public class SetUp {

    //String token = getToken("admin", "password123");

    @BeforeEach
    @Step("Setup base Config and Request Spec")
    public void setUp() {
        baseURI = "https://restful-booker.herokuapp.com";
        //basePath = "/booking/";
        //RestAssured.port = 8080;
        config = config()
                .encoderConfig(EncoderConfig
                        .encoderConfig()
                        .defaultContentCharset("UTF-8"));

        requestSpecification = new RequestSpecBuilder()
                //.addHeader("token", token)
                .addHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .build()
                .filter(new AllureRestAssured()
                        .setRequestTemplate("http-request.ftl")
                        .setResponseTemplate("http-response.ftl")
                );
    }
}
