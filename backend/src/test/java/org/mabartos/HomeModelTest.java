package org.mabartos;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.api.service.HomeService;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HomeModelTest {

    @Inject
    HomeService homeService;

    HomeModel home1, home2;

    private final String DEFAULT_BROKER = "tcp:localhost";
    private final String DEFAULT_IMAGE = "image.jpg";

    @BeforeEach
    public void setUp() {
        HomeModel first = new HomeModel("home1");
        first.setBrokerURL(DEFAULT_BROKER);
        first.setImageURL(DEFAULT_IMAGE);
        HomeModel second = new HomeModel("home2");
        home1 = homeService.create(first);
        home2 = homeService.create(second);
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
        HomeModel item = given().when()
                .get("/homes/" + home1.getID())
                .then()
                .statusCode(200)
                .body("id", is(home1.getID().intValue()))
                .body("name", is("home1"))
                .body("brokerURL", is(DEFAULT_BROKER))
                .body("imageURL", is(DEFAULT_IMAGE))
                .assertThat()
                .extract()
                .as(HomeModel.class);

        assertThat(item, is(home1));
    }

    @Test
    public void testUpdateHome() {
        assertThat(home1.getBrokerURL(), is(DEFAULT_BROKER));
        assertThat(home1.getImageURL(), is(DEFAULT_IMAGE));

        HomeModel update = home1;
        update.setBrokerURL("broker.com");
        update.setImageURL("imageUpdate.jpg");

        HomeModel updated = homeService.updateByID(home1.getID(), update);
        assertThat(updated, notNullValue());
        assertThat(home1.getBrokerURL(), is("broker.com"));
        assertThat(home1.getImageURL(), is("imageUpdate.jpg"));

        update = new HomeModel();
        update.setID(home1.getID());
        update.setName(home1.getName());
        update.setImageURL(home1.getImageURL());
        update.setTopic("home/default/topic/something");
        update.setBrokerURL(home1.getBrokerURL());

        updated = given().contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .body(update)
                .patch("/homes/" + home1.getID())
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .as(HomeModel.class);

        assertThat(updated, notNullValue());
        assertThat(updated.getTopic(), is("home/default/topic/something"));
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

