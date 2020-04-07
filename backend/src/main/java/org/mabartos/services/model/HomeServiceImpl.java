package org.mabartos.services.model;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.HomeService;
import org.mabartos.controller.data.HomeData;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.MqttClientModel;
import org.mabartos.persistence.model.UserModel;
import org.mabartos.persistence.repository.HomeRepository;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Set;
import java.util.UUID;

@Dependent
public class HomeServiceImpl extends CRUDServiceImpl<HomeModel, HomeRepository, Long> implements HomeService {

    private AppServices services;

    @Inject
    HomeServiceImpl(HomeRepository repository, AppServices services) {
        super(repository);
        this.services = services;
    }

    public void start(@Observes StartupEvent event) {
    }

    @Override
    public HomeModel create(HomeModel home) {
        try {
            if (!getRepository().isPersistent(home)) {
                MqttClientModel client = new MqttClientModel(home, home.getBrokerURL());
                home.setMqttClient(client);
                client.persist();
                getRepository().persist(home);
                getRepository().flush();
            }
            return home;
        } catch (Exception e) {
            // HIBERNATE BUG
            e.printStackTrace();
            return home;
        }
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
    public boolean addDeviceToHome(DeviceModel device, Long homeID) {
        try {
            HomeModel found = super.findByID(homeID);
            if (found != null && device != null) {
                found.addDevice(device);
                device.setHome(found);
                getEntityManager().merge(found);
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
            HomeData data = HomeData.fromJson(JSON);
            home.setName(data.getName());
            home.setBrokerURL(data.getBrokerURL());
            return updateByID(homeID, home);
        }
        return null;
    }

    @Override
    public boolean deleteByID(Long id) {
        HomeModel found = super.findByID(id);
        if (found != null) {
            services.rooms().deleteAllFromHome(found.getID());
            services.devices().deleteAllFromHome(found.getID());
            services.invitations().deleteAllFromHome(found.getID());

            return super.deleteByID(id);
        }
        return false;
    }
}
