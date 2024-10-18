package org.com.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class JokeControllerTest {

    @Test
    public void testGetJokes_success() {
        RestAssured.given()
                .queryParam("count", 10)
                .when().get("/jokes")
                .then()
                .statusCode(200)
                .body("size()", equalTo(10));
    }

    @Test
    public void testGetJokes_invalidCount() {
        RestAssured.given()
                .queryParam("count", 5)
                .when().get("/jokes")
                .then()
                .statusCode(400)
                .body("message", equalTo("The count must be 10 or greater."));
    }

    @Test
    public void testGetJokes_negativeCount() {
        RestAssured.given()
                .queryParam("count", -1)
                .when().get("/jokes")
                .then()
                .statusCode(400)
                .body("message", containsString("Count must be greater than 0"));
    }
}
