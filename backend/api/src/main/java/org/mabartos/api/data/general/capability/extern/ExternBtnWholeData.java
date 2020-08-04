/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.capability.extern;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.service.capability.CapabilityType;
import org.mabartos.api.data.general.capability.manage.CapabilityUtils;
import org.mabartos.api.data.general.capability.manage.CapabilityWholeData;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.extern.ExternBtnCapModel;
import org.mabartos.persistence.jpa.model.services.capability.extern.ExternBtnCapEntity;

public class ExternBtnWholeData extends CapabilityWholeData {

    @JsonProperty(ExternBtnCapModel.HAS_TWO_STATES_JSON)
    private boolean hasTwoStates;

    public ExternBtnWholeData(@JsonProperty(ExternBtnCapModel.HAS_TWO_STATES_JSON) boolean hasTwoStates,
                              Integer pin) {
        super(CapabilityType.EXTERN_BTN, pin);
        this.hasTwoStates = hasTwoStates;
    }

    public ExternBtnWholeData(Long id, CapabilityType type, Integer pin) {
        super(id, type, pin);
    }

    public boolean hasTwoStates() {
        return this.hasTwoStates;
    }

    public void setHasTwoStates(boolean state) {
        this.hasTwoStates = state;
    }

    @Override
    public CapabilityModel toModel() {
        ExternBtnCapModel entity = new ExternBtnCapEntity(CapabilityUtils.getRandomNameForCap(getType()), getPin());
        entity.setHasTwoStates(hasTwoStates());
        return entity;
    }
}
