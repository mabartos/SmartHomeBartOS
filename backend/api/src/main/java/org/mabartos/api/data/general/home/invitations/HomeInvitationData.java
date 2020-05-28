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
import org.mabartos.api.data.general.SerializeUtils;

import java.util.UUID;

@JsonPropertyOrder({"id", "issuerID", "receiverID", "homeID"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeInvitationData {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("issuerID")
    private String issuerID;

    @JsonProperty("receiverID")
    private String receiverID;

    @JsonProperty("homeID")
    private Long homeID;

    @JsonCreator
    public HomeInvitationData(@JsonProperty("id") Long id,
                              @JsonProperty("issuerID") String issuerID,
                              @JsonProperty("receiverID") String receiverID,
                              @JsonProperty("homeID") Long homeID) {
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
