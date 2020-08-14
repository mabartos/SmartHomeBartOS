/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.capability;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.controller.capability.CapabilityInfoData;
import org.mabartos.api.data.general.capability.extern.ExternBtnWholeData;
import org.mabartos.api.data.general.capability.manage.CapabilityUtils;
import org.mabartos.api.data.general.capability.manage.CapabilityWholeData;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.protocol.mqtt.BartMqttClient;
import org.mabartos.api.protocol.mqtt.MqttClientManager;
import org.mabartos.api.protocol.mqtt.TopicUtils;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.capability.CapabilityService;
import org.mabartos.api.service.capability.triggers.TriggerService;
import org.mabartos.persistence.jpa.model.services.capability.extern.ExternBtnCapEntity;
import org.mabartos.persistence.jpa.repository.CapabilityRepository;
import org.mabartos.services.model.CRUDServiceImpl;
import org.mabartos.services.utils.DataToModelBase;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Dependent
public class CapabilityServiceImpl extends CRUDServiceImpl<CapabilityModel, CapabilityEntity, CapabilityRepository, Long> implements CapabilityService {

    AppServices services;
    TriggerService triggerService;

    @Inject
    CapabilityServiceImpl(CapabilityRepository repository, AppServices services, TriggerService triggerService) {
        super(repository);
        this.services = services;
        this.triggerService = triggerService;
    }

    public void start(@Observes StartupEvent event) {
    }

    @Override
    public boolean deleteByID(Long id) {
        clearRetainedMessages(id);
        return super.deleteByID(id);
    }

    @Override
    public TriggerService triggers() {
        return this.triggerService;
    }

    public CapabilityModel createFromJSON(String JSON) {
        CapabilityModel entity = DataToModelBase.toCapabilityModel(CapabilityInfoData.fromJSON(JSON));
        return create(entity);
    }

    @Override
    public void clearRetainedMessages(Long id) {
        CapabilityModel cap = findByID(id);
        String topic = TopicUtils.getCapabilityTopic(cap);
        if (topic != null) {
            BartMqttClient client = services.mqttManager().getMqttForHome(cap.getDevice().getHomeID());
            MqttClientManager.clearRetainedMessages(client, topic);
        }
    }

    @Override
    public CapabilityModel updateFromJson(Long ID, String JSON) {
        CapabilityModel cap = getRepository().findById(ID);
        if (cap != null) {
            CapabilityInfoData data = CapabilityInfoData.fromJSON(JSON);
            cap = DataToModelBase.toCapabilityModel(data, cap);

            if (!cap.getDeviceID().equals(data.getDeviceID())) {
                cap.setDevice(services.devices().findByID(data.getDeviceID()));
            }
            return updateByID(ID, cap);
        }
        return null;
    }

    @Override
    public CapabilityModel fromDataToModel(CapabilityWholeData data) {
        if (data != null) {
            if (data instanceof ExternBtnWholeData) {
                ExternBtnCapEntity entity = new ExternBtnCapEntity(CapabilityUtils.getRandomNameForCap(data.getType()), data.getPin());
                entity.setHasTwoStates(((ExternBtnWholeData) data).hasTwoStates());
                return entity;
            }
            return new CapabilityEntity(CapabilityUtils.getRandomNameForCap(data.getType()), data.getType(), data.getPin());
        }
        return null;
    }

    @Override
    public Set<CapabilityModel> fromDataToModel(Set<CapabilityWholeData> caps) {
        if (caps != null) {
            Set<CapabilityModel> result = new HashSet<>();
            caps.forEach(cap -> result.add(fromDataToModel(cap)));
            return result;
        }
        return null;
    }
}
