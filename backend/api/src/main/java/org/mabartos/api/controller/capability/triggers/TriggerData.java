/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.capability.triggers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.data.general.SerializeUtils;
import org.mabartos.api.model.events.trigger.InputTriggerStates;
import org.mabartos.api.model.events.trigger.OutputTriggerStates;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TriggerData {

    @JsonProperty(JsonPropertyNames.TRIGGER_INPUT_CAP_ID)
    private Long inputCapID;

    @JsonProperty(JsonPropertyNames.TRIGGER_OUTPUT_CAP_ID)
    private Long outputCapID;

    @JsonProperty(JsonPropertyNames.TRIGGER_INPUT_STATE)
    private InputTriggerStates inputState;

    @JsonProperty(JsonPropertyNames.TRIGGER_OUTPUT_STATE)
    private OutputTriggerStates outputState;

    public TriggerData(@JsonProperty(JsonPropertyNames.TRIGGER_INPUT_CAP_ID) Long inputCapID,
                       @JsonProperty(JsonPropertyNames.TRIGGER_OUTPUT_CAP_ID) Long outputCapID,
                       @JsonProperty(JsonPropertyNames.TRIGGER_INPUT_STATE) InputTriggerStates inputState,
                       @JsonProperty(JsonPropertyNames.TRIGGER_OUTPUT_STATE) OutputTriggerStates outputState) {
        this.inputCapID = inputCapID;
        this.outputCapID = outputCapID;
        this.inputState = inputState;
        this.outputState = outputState;
    }

    public Long getInputCapID() {
        return inputCapID;
    }

    public void setInputCapID(Long inputCapID) {
        this.inputCapID = inputCapID;
    }

    public Long getOutputCapID() {
        return outputCapID;
    }

    public void setOutputCapID(Long outputCapID) {
        this.outputCapID = outputCapID;
    }

    public InputTriggerStates getInputState() {
        return inputState;
    }

    public void setInputState(InputTriggerStates inputState) {
        this.inputState = inputState;
    }

    public OutputTriggerStates getOutputState() {
        return outputState;
    }

    public void setOutputState(OutputTriggerStates outputState) {
        this.outputState = outputState;
    }

    public static TriggerData fromJSON(String JSON) {
        return SerializeUtils.fromJson(JSON, TriggerData.class);
    }
}
