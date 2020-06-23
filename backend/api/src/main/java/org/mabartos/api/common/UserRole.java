/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.common;

public enum UserRole {
    SYS_ADMIN(1000),
    HOME_ADMIN(10),
    HOME_MEMBER(9),
    HOME_CHILD(8);

    private int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValueAbility() {
        return this.value;
    }

    public boolean moreValuableOrEqualThan(UserRole item) {
        return this.value >= item.getValueAbility();
    }

    public boolean isLessValueAbleThan(UserRole item) {
        return this.value < item.getValueAbility();
    }

}
