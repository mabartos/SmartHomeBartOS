/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.home;

import org.mabartos.api.common.Identifiable;
import org.mabartos.api.model.user.UserModel;

import java.util.UUID;

public interface HomeInvitationModel extends Identifiable<Long> {

    UUID getIssuerID();

    void setIssuerID(UUID issuerID);

    UserModel getReceiver();

    void setReceiver(UserModel receiver);

    HomeModel getHome();

    void setHome(HomeModel home);

    /* COMPUTED */

    UUID getReceiverID();

    Long getHomeID();

    String getHomeName();

    boolean equalsWithoutID(Object obj);
}
