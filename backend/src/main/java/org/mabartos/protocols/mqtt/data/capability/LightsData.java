package org.mabartos.protocols.mqtt.data.capability;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.LightCapModel;
import org.mabartos.protocols.mqtt.data.CapabilityData;
import org.mabartos.protocols.mqtt.utils.MqttSerializeUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LightsData extends CapabilityData {

    @JsonProperty("isTurnedOn")
    protected boolean isTurnedOn;

    @JsonProperty("intensity")
    protected Double intensity;

    @JsonProperty("minIntensity")
    protected Double minIntensity;

    @JsonCreator
    public LightsData(@JsonProperty("id") Long id,
                      @JsonProperty("name") String name,
                      @JsonProperty("type") CapabilityType type,
                      @JsonProperty("isTurnedOn") boolean state,
                      @JsonProperty("intensity") Double intensity,
                      @JsonProperty("minIntensity") Double minIntensity) {
        super(id, name, type);
        this.isTurnedOn = state;
        this.intensity = intensity;
        this.minIntensity = minIntensity;
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public Double getIntensity() {
        return intensity;
    }

    public Double getMinIntensity() {
        return minIntensity;
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model != null) {
            super.editModel(model);
            LightCapModel result = (LightCapModel) model;
            result.setState(this.isTurnedOn());
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
