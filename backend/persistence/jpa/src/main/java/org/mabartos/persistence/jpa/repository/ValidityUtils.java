/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.repository;


import org.mabartos.api.model.home.HomeModel;

public class ValidityUtils {

    public static boolean isBrokerURLValid(HomeModel home) {
        if (home != null && home.getBrokerURL() != null) {
            String pattern = "(tcp|http|https)://.+:*.*";
            String brokerURL = home.getBrokerURL();
            return brokerURL.matches(pattern);
        }
        return false;
    }
}
