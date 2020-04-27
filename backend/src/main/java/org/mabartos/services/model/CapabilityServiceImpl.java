package org.mabartos.services.model;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.protocol.MqttClientManager;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.CapabilityService;
import org.mabartos.controller.data.CapabilityData;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.repository.CapabilityRepository;
import org.mabartos.protocols.mqtt.utils.TopicUtils;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Dependent
public class CapabilityServiceImpl extends CRUDServiceImpl<CapabilityModel, CapabilityRepository, Long> implements CapabilityService {

    AppServices services;

    @Inject
    CapabilityServiceImpl(CapabilityRepository repository, AppServices services) {
        super(repository);
        this.services = services;
    }

    public void start(@Observes StartupEvent event) {
    }

    @Override
    public boolean deleteByID(Long id) {
        clearRetainedMessages(id);
        return super.deleteByID(id);
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
            CapabilityData data = CapabilityData.fromJSON(JSON);
            cap.setName(data.getName());
            cap.setEnabled(data.isEnabled());
            cap.setType(data.getType());
            cap.setPin(data.getPin());

            if (!cap.getDeviceID().equals(data.getDeviceID())) {
                cap.setDevice(services.devices().findByID(data.getDeviceID()));
            }
            return updateByID(ID, cap);
        }
        return null;
    }
}
