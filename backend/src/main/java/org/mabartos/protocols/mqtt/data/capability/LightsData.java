package org.mabartos.protocols.mqtt.data.capability;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.capability.LightCapModel;
import org.mabartos.protocols.mqtt.data.CapabilityData;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

public class LightsData extends CapabilityData {

    @JsonProperty("state")
    protected State state;

    @JsonProperty("intensity")
    protected Double intensity;

    @JsonProperty("minIntensity")
    protected Double minIntensity;

    @JsonCreator
    public LightsData(@JsonProperty("id") Long id,
                      @JsonProperty("name") String name,
                      @JsonProperty("type") CapabilityType type,
                      @JsonProperty("state") State state,
                      @JsonProperty("intensity") Double intensity,
                      @JsonProperty("minIntensity") Double minIntensity) {
        super(id, name, type);
        this.state = state;
        this.intensity = intensity;
        this.minIntensity = minIntensity;
    }

    public State getState() {
        return state;
    }

    public Double getIntensity() {
        return intensity;
    }

    public Double getMinIntensity() {
        return minIntensity;
    }

    @Override
    public LightCapModel toModel() {
        LightCapModel model = new LightCapModel(this.name);
        model.setID(this.id);
        model.setType(this.type);
        model.setState(this.state);
        model.setIntensity(this.intensity);
        model.setMinIntensity(this.minIntensity);
        return model;
    }

    public static LightsData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, LightsData.class);
    }


}
