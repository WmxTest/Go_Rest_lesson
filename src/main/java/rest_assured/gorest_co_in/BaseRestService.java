package rest_assured.gorest_co_in;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static utils.PropertyLoader.*;

public class BaseRestService {

    static RequestSpecification requestSpecification;

    static {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        requestSpecification = given()
                .auth()
                .oauth2(getProperty("go.rest.token"));
        requestSpecification.baseUri("https://gorest.co.in/public");
    }
}