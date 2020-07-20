/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.home;

import com.google.common.collect.Sets;
import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.controller.user.HomeMemberRoleData;
import org.mabartos.api.data.general.home.HomeData;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.model.user.UserRoleModel;
import org.mabartos.api.protocol.mqtt.BartMqttClient;
import org.mabartos.api.protocol.mqtt.MqttClientManager;
import org.mabartos.api.protocol.mqtt.TopicUtils;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.home.HomeInvitationService;
import org.mabartos.api.service.home.HomeService;
import org.mabartos.persistence.jpa.model.mqtt.MqttClientEntity;
import org.mabartos.persistence.jpa.repository.HomeRepository;
import org.mabartos.services.model.CRUDServiceImpl;
import org.mabartos.services.utils.DataToModelBase;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.Query;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Dependent
public class HomeServiceImpl extends CRUDServiceImpl<HomeModel, HomeEntity, HomeRepository, Long> implements HomeService {

    private AppServices services;
    private HomeInvitationService invitationService;

    @Inject
    HomeServiceImpl(HomeRepository repository, AppServices services, HomeInvitationService invitationService) {
        super(repository);
        this.services = services;
        this.invitationService = invitationService;
    }

    public void start(@Observes StartupEvent event) {
    }

    @Override
    public HomeModel create(HomeModel home) {
        try {
            if (!getRepository().isPersistent((HomeEntity) home)) {
                MqttClientEntity client = new MqttClientEntity(home, home.getBrokerURL());
                home.setMqttClient(client);
                client.persist();
                getRepository().persist((HomeEntity) home);
                getRepository().flush();
            }
            return home;
        } catch (Exception e) {
            // HIBERNATE BUG
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HomeModel addUserToHome(UUID userID, Long homeID) {
        try {
            HomeModel home = getRepository().findById(homeID);
            UserModel user = services.users().findByID(userID);
            if (home != null && user != null) {
                home.addUser(user);
                user.addHome(home);
                return updateByID(homeID, home);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HomeInvitationService invitations() {
        return this.invitationService;
    }

    @Override
    public boolean addDeviceToHome(DeviceModel device, Long homeID) {
        try {
            HomeModel found = super.findByID(homeID);
            if (found != null && device != null) {
                found.addDevice(device);
                device.setHome(found);
                getEntityManager().merge(device);
                getEntityManager().flush();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeDeviceFromHome(DeviceModel device, Long homeID) {
        try {
            HomeModel found = super.findByID(homeID);
            if (found != null && device != null) {
                found.removeDeviceFromHome(device);
                getEntityManager().merge(found);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Set<DeviceModel> getAllUnAssignedDevices(Long homeID) {
        HomeModel found = super.findByID(homeID);
        if (found != null) {
            return found.getUnAssignedDevices();
        }
        return null;
    }

    @Override
    public HomeModel updateFromJson(Long homeID, String JSON) {
        HomeModel home = getRepository().findById(homeID);
        if (home != null) {
            home = DataToModelBase.toHomeModel(HomeData.fromJson(JSON), home);
            return updateByID(homeID, home);
        }
        return null;
    }

    @Override
    public boolean deleteByID(Long id) {
        HomeModel found = super.findByID(id);
        if (found != null) {
            Long foundID = found.getID();
            services.devices().deleteAllFromHome(foundID);
            services.rooms().deleteAllFromHome(foundID);
            services.homes().invitations().deleteAllFromHome(foundID);
            services.users().roles().deleteAllRolesFromHome(foundID);

            clearRetainedMessages(id);
            return super.deleteByID(id);
        }
        return false;
    }

    @Override
    public void clearRetainedMessages(Long id) {
        HomeModel home = findByID(id);
        if (home != null) {
            BartMqttClient client = services.mqttManager().getMqttForHome(id);
            MqttClientManager.clearRetainedMessages(client, TopicUtils.getHomeTopic(id));
            home.getUnAssignedDevices().forEach(dev -> services.devices().clearRetainedMessages(dev.getID()));
            home.getChildren().forEach(room -> services.rooms().clearRetainedMessages(room.getID()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<UserRoleModel> getAllHomeMembers(Long homeID) {
        Query query = getEntityManager().createNamedQuery("getAllHomeMembers");
        query.setParameter("homeID", homeID);
        return Sets.newHashSet(query.getResultList());
    }

    @Override
    public Set<HomeMemberRoleData> getAllHomeMembersData(Long homeID) {
        Set<UserRoleModel> members = getAllHomeMembers(homeID);
        Set<HomeMemberRoleData> data = new HashSet<>();
        if (members != null) {
            members.forEach(member -> data.add(new HomeMemberRoleData(homeID, member.getRole(), member.getUser())));
            return data;
        }
        return Collections.emptySet();
    }
}
