package org.mabartos.persistence;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.protocol.MqttClientManager;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.model.UserModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class DataLoader {

    @Inject
    AppServices services;

    @Inject
    MqttClientManager manager;

    @Inject
    public DataLoader() {
    }

    public void addRecords(@Observes StartupEvent start) {
        addDefaultUser();
        addHomes();
        addUsersToHomes();
        manager.initAllClients();
    }

    private UserModel addDefaultUser() {
        UserModel userDefault = new UserModel();
        userDefault.setID(UUID.randomUUID());
        userDefault.setUsername("admin");
        userDefault.setEmail("userDefault@gmail.com");
        return services.users().create(userDefault);
    }

    private void addHomes() {
        UserModel user = services.users().findByUsername("admin");

        final int CNT = 3;
        for (int i = 0; i < CNT; i++) {
            HomeModel home = new HomeModel();
            StringBuilder builder = new StringBuilder();
            home.setName(builder.append("home").append(i + 2).toString());
            home.setBrokerURL("tcp://127.0.0.1:1883");
            HomeModel created = services.homes().create(home);

            if (user != null) {
                services.homes().addUserToHome(user.getID(), created.getID());
            }
        }
        HomeModel home = new HomeModel();
        home.setName("home1");
        home.setBrokerURL("tcp://127.0.0.1:1883");
        if (user != null) {
            home.addUser(user);
        }
        HomeModel created = services.homes().create(home);
        if (user != null) {
            services.homes().addUserToHome(user.getID(), created.getID());
        }

        home = new HomeModel();
        home.setName("homeDefault");
        home.setBrokerURL("tcp://127.0.0.1:1212");
        RoomModel room = new RoomModel("room1");
        home.addChild(room);
        room.setHome(home);

        created = services.homes().create(home);
        if (user != null) {
            services.homes().addUserToHome(user.getID(), created.getID());
        }
    }

    private void addUsersToHomes() {
        // services.homes().addUserToHome((long) 1, (long) 12);
    }
}
