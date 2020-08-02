/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general.home.invitations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mabartos.api.data.general.JsonPropertyNames;
import org.mabartos.api.data.general.SerializeUtils;

import java.util.UUID;

@JsonPropertyOrder({JsonPropertyNames.ID, JsonPropertyNames.ISSUER_ID, JsonPropertyNames.RECEIVER_ID, JsonPropertyNames.HOME_ID})
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeInvitationData {

    @JsonProperty(JsonPropertyNames.ID)
    private Long id;

    @JsonProperty(JsonPropertyNames.ISSUER_ID)
    private String issuerID;

    @JsonProperty(JsonPropertyNames.RECEIVER_ID)
    private String receiverID;

    @JsonProperty(JsonPropertyNames.HOME_ID)
    private Long homeID;

    @JsonCreator
    public HomeInvitationData(@JsonProperty(JsonPropertyNames.ID) Long id,
                              @JsonProperty(JsonPropertyNames.ISSUER_ID) String issuerID,
                              @JsonProperty(JsonPropertyNames.RECEIVER_ID) String receiverID,
                              @JsonProperty(JsonPropertyNames.HOME_ID) Long homeID) {
        this.id = id;
        this.issuerID = issuerID;
        this.receiverID = receiverID;
        this.homeID = homeID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getIssuerID() {
        return UUID.fromString(issuerID);
    }

    public void setIssuerID(String issuerID) {
        this.issuerID = issuerID;
    }

    public UUID getReceiverID() {
        return (receiverID != null) ? UUID.fromString(receiverID) : null;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public Long getHomeID() {
        return homeID;
    }

    public void setHomeID(Long homeID) {
        this.homeID = homeID;
    }

    public static HomeInvitationData fromJSON(String JSON) {
        return SerializeUtils.fromJson(JSON, HomeInvitationData.class);
    }
}
