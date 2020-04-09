package org.mabartos.controller.home;

import io.quarkus.security.Authenticated;
import org.mabartos.api.controller.home.HomeResource;
import org.mabartos.api.controller.home.HomesResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.authz.annotations.HasRoleInHome;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.general.CapabilityType;
import org.mabartos.general.UserRole;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.room.RoomModel;
import org.mabartos.persistence.model.capability.TemperatureCapModel;
import org.mabartos.persistence.model.home.HomeModel;
import org.mabartos.persistence.model.user.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/homes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@Authenticated
@RequestScoped
public class HomesResourceProvider implements HomesResource {
    public static Logger logger = Logger.getLogger(HomesResourceProvider.class.getName());

    private BartSession session;

    @Inject
    public HomesResourceProvider(BartSession session) {
        this.session = session;
        this.session.initEnvironment();
    }


    //TODO test init
    @GET
    @Path("/init")
    public HomeModel init() {
        HomeModel home = new HomeModel("homeTest");
        UserModel user = session.auth().getUserInfo();
        home.addUser(user);
        user.addHome(home);
        home.setBrokerURL("tcp://localhost:1883");
        home = session.services().homes().create(home);
        RoomModel room = new RoomModel("roomTest");
        room.setHome(home);
        home.addChild(room);
        room = session.services().rooms().create(room);

        DeviceModel device = new DeviceModel("device1");
        device.setRoom(room);
        device.setHome(home);
        device = session.services().devices().create(device);

        CapabilityModel cap1 = new CapabilityModel("cap1", CapabilityType.LIGHT);
        TemperatureCapModel cap2 = new TemperatureCapModel("temp1");
        cap1.setDevice(device);
        cap2.setDevice(device);
        device.addCapability(cap1);
        device.addCapability(cap2);
        session.services().capabilities().create(cap1);
        session.services().capabilities().create(cap2);
        return home;
    }

    @GET
    public Response getAll() {
        UserModel user = session.auth().getUserInfo();
        if (user != null) {
            return Response.ok(user.getHomes()).build();
        }
        return Response.status(400).build();
    }

    @POST
    public HomeModel createHome(@Valid HomeModel home) {
        UserModel user = session.auth().getUserInfo();
        if (user != null) {
            home.addUser(user, UserRole.HOME_ADMIN);
            user.addHome(home);
            return session.services().homes().create(home);
        }
        return null;
    }

    @POST
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN)
    @Path("/add" + HOME_ID)
    public HomeModel addHomeToUser(@PathParam(HOME_ID_NAME) Long id) {
        return session.services().homes().addUserToHome(session.auth().getID(), id);
    }

    @Path(HOME_ID)
    public HomeResource forwardToHome(@PathParam(HOME_ID_NAME) Long id) {
        if (ControllerUtil.existsItem(session.services().homes(), id)
                && (session.getActualUser() == null || (ControllerUtil.containsItem(session.getActualUser().getHomes(), id)))) {
            return new HomeResourceProvider(session.setActualHome(id));
        }
        return null;
    }
}
