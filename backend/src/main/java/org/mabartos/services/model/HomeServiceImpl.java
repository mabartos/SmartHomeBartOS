package org.mabartos.services.model;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.service.HomeService;
import org.mabartos.api.service.RoomService;
import org.mabartos.api.service.UserService;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.MqttClientModel;
import org.mabartos.persistence.model.UserModel;
import org.mabartos.persistence.repository.HomeRepository;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Set;

@Dependent
public class HomeServiceImpl extends CRUDServiceImpl<HomeModel, HomeRepository> implements HomeService {

    private UserService userService;
    private RoomService roomService;

    @Inject
    HomeServiceImpl(HomeRepository repository, UserService userService, RoomService roomService) {
        super(repository);
        this.userService = userService;
        this.roomService = roomService;
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
    public boolean addUserToHome(Long userID, Long homeID) {
        try {
            HomeModel home = getRepository().findById(homeID);
            UserModel user = userService.findByID(userID);
            if (home != null && user != null) {
                user.addChild(home);
                home.addUser(user);
                getRepository().persistAndFlush(home);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
    public boolean deleteByID(Long id) {
        HomeModel found = super.findByID(id);
        if (found != null) {
            found.getChildren().forEach(f -> {
                roomService.deleteByID(f.getID());
            });
            return super.deleteByID(id);
        }
        return false;
    }
}
