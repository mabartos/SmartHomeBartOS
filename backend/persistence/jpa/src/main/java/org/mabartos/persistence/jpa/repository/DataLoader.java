/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.repository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataLoader {
/*
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
    }

    private UserModel addDefaultUser() {
        UserModel userDefault = new UserEntity();
        userDefault.setID(UUID.randomUUID());
        userDefault.setUsername("admin");
        userDefault.setEmail("userDefault@gmail.com");
        return services.users().create(userDefault);
    }

    private void addHomes() {
        UserModel user = services.users().findByUsername("admin");

        final int CNT = 3;
        for (int i = 0; i < CNT; i++) {
            HomeModel home = new HomeEntity();
            StringBuilder builder = new StringBuilder();
            home.setName(builder.append("home").append(i + 2).toString());
            home.setBrokerURL("tcp://127.0.0.1:1883");
            HomeModel created = services.homes().create(home);

            if (user != null) {
                services.homes().addUserToHome(user.getID(), created.getID());
            }
        }
        HomeModel home = new HomeEntity();
        home.setName("home1");
        home.setBrokerURL("tcp://127.0.0.1:1883");
        if (user != null) {
            home.addUser(user);
        }
        HomeModel created = services.homes().create(home);
        if (user != null) {
            services.homes().addUserToHome(user.getID(), created.getID());
        }

        home = new HomeEntity();
        home.setName("homeDefault");
        home.setBrokerURL("tcp://127.0.0.1:1212");
        RoomModel room = new RoomEntity("room1");
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

 */
}
