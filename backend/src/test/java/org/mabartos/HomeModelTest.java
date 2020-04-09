package org.mabartos;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.home.HomeModel;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HomeModelTest {

    @Inject
    AppServices services;

    HomeModel home1, home2;

    private final String DEFAULT_BROKER = "tcp:localhost";
    private final String DEFAULT_IMAGE = "image.jpg";

    @BeforeEach
    public void setUp() {
        HomeModel first = new HomeModel("home1");
        first.getMqttClient().setBrokerURL(DEFAULT_BROKER);
        HomeModel second = new HomeModel("home2");
        home1 = services.homes().create(first);
        home2 = services.homes().create(second);
    }

    @Test
    public void testCreatedHomes() {
        given().when()
                .get("/homes")
                .then()
                .statusCode(200)
                .body("name", hasItems("home1", "home2"))
                .assertThat();
    }

    @Test
    public void testGetIndividual() {
        given().when()
                .get("/homes/" + home1.getID())
                .then()
                .statusCode(200)
                .body("id", is(home1.getID().intValue()))
                .body("name", is("home1"))
                .body("mqttClientID", anything())
                .assertThat();
    }

    @Test
    public void testUpdateHome() {
        assertThat(home1.getMqttClient().getBrokerURL(), is(DEFAULT_BROKER));

        HomeModel update = home1;
        update.getMqttClient().setBrokerURL("broker.com");

        HomeModel updated = services.homes().updateByID(home1.getID(), update);
        assertThat(updated, notNullValue());
        assertThat(home1.getMqttClient().getBrokerURL(), is("broker.com"));

        update = new HomeModel();
        update.setID(home1.getID());
        update.setName(home1.getName());
        update.getMqttClient().setTopic("home/default/topic/something");
        update.getMqttClient().setBrokerURL(home1.getMqttClient().getBrokerURL());

        updated = given().contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .body(update)
                .patch("/homes/" + home1.getID())
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .as(HomeModel.class);

        assertThat(updated, notNullValue());
        assertThat(updated.getMqttClient().getTopic(), is("home/default/topic/something"));
        assertThat(updated, is(update));
    }

    @Test
    public void testRemoveHomes() {
        given().when()
                .delete("/homes/" + home1.getID())
                .then()
                .statusCode(200)
                .assertThat();

        given().when()
                .get("/homes")
                .then()
                .statusCode(200)
                .body("name", hasItems("home2"))
                .assertThat();
    }
}

