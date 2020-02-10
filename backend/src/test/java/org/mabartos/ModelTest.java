package org.mabartos;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.service.core.HomeService;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ModelTest {

    @Inject
    HomeService homeService;

    @BeforeAll
    public void setUp() {
        HomeModel home1 = new HomeModel("home1");
        home1.setBrokerURL("tcp:localhost");
        HomeModel home2 = new HomeModel("home2");
        homeService.create(home1);
        homeService.create(home2);
    }

    @Test
    public void testCreatedHomes() {
        given()
                .when().get("/homes")
                .then()
                .statusCode(200)
                .body("name", hasItems("home1", "home2"))
                .body("brokerURL", contains("tcp:localhost"));
    }

    @Test
    public void testCreateUsers() {
    }
}

