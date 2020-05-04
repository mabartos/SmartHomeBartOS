package org.mabartos.api.data.general.capability.light;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.data.general.SerializeUtils;
import org.mabartos.api.data.general.capability.CapabilityDataWithState;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.capability.light.LightCapModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LightsData extends CapabilityDataWithState {

    @JsonProperty("intensity")
    protected Byte intensity;

    @JsonProperty("minIntensity")
    protected Byte minIntensity;

    @JsonCreator
    public LightsData(@JsonProperty("isTurnedOn") boolean state,
                      @JsonProperty("intensity") Byte intensity,
                      @JsonProperty("minIntensity") Byte minIntensity) {
        super(state);
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

    public void setMinIntensity(Byte minIntensity) {
        this.minIntensity = minIntensity;
    }

    @Override
    public CapabilityModel editModel(CapabilityModel model) {
        if (model instanceof LightCapModel) {
            super.editModel(model);
            LightCapModel result = (LightCapModel) model;
            result.setType(CapabilityType.LIGHT);
            result.setIntensity(this.getIntensity());
            result.setMinIntensity(this.getMinIntensity());
            return result;
        }
        return null;
    }

    public static LightsData fromJson(String json) {
        return SerializeUtils.fromJson(json, LightsData.class);
    }
}
