package org.mabartos.protocols.mqtt.data.capability;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.LightCapModel;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LightsData extends CapabilityDataWithState {

    @JsonProperty("intensity")
    protected Byte intensity;

    @JsonProperty("minIntensity")
    protected Byte minIntensity;

    @JsonCreator
    public LightsData(@JsonProperty("id") Long id,
                      @JsonProperty("type") CapabilityType type,
                      @JsonProperty("pin") Integer pin,
                      @JsonProperty("isTurnedOn") boolean state,
                      @JsonProperty("intensity") Byte intensity,
                      @JsonProperty("minIntensity") Byte minIntensity) {
        super(id, type, pin, state);
        this.intensity = intensity;
        this.minIntensity = minIntensity;
    }

    public Byte getIntensity() {
        return intensity;
    }

    public Byte getMinIntensity() {
        return minIntensity;
    }

    public void setIntensity(Byte intensity) {
        this.intensity = intensity;
    }

    public void minIntensity(Byte minIntensity) {
        this.minIntensity = minIntensity;
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof LightCapModel) {
            super.editModel(model);
            LightCapModel result = (LightCapModel) model;
            result.setState(this.isTurnedOn());
            result.setType(CapabilityType.LIGHT);
            result.setIntensity(this.getIntensity());
            result.setMinIntensity(this.getMinIntensity());
            return result;
        }
        return null;
    }

    public static LightsData fromJson(String json) {
        return MqttSerializeUtils.fromJson(json, LightsData.class);
    }
}
