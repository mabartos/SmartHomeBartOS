package org.mabartos.services.model.capability;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.controller.capability.CapabilityInfoData;
import org.mabartos.api.data.general.capability.manage.CapabilityUtils;
import org.mabartos.api.data.general.capability.manage.CapabilityWholeData;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.protocol.mqtt.BartMqttClient;
import org.mabartos.api.protocol.mqtt.MqttClientManager;
import org.mabartos.api.protocol.mqtt.TopicUtils;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.capability.CapabilityService;
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
    public Set<CapabilityModel> fromDataToModel(Set<CapabilityWholeData> caps) {
        if (caps != null) {
            Set<CapabilityModel> result = new HashSet<>();
            caps.forEach(cap -> result.add(new CapabilityEntity(CapabilityUtils.getRandomNameForCap(cap.getType()), cap.getType(), cap.getPin())));
            return result;
        }
        return null;
    }
}
