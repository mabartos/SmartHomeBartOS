package org.mabartos.streams.mqtt.devices;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.service.core.DeviceService;

@SuppressWarnings("unchecked")
public class GeneralMqttDevice<Model> {

    protected DeviceService deviceService;
    protected String message;
    protected Model model;
    protected DeviceType type;

    public GeneralMqttDevice(DeviceService deviceService, DeviceType type, Long id, String message) {
        this.deviceService = deviceService;
        this.message = message;
        this.type = type;
        DeviceModel found = deviceService.findByID(id);
        if (found != null && found.getType().equals(type)) {
            this.model = (Model) found;
        }
    }

    public void parseMessage() {
        System.out.println("Device type: " + type + ", message: " + message);
    }
}
