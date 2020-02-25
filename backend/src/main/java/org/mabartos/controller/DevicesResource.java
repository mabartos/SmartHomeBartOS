package org.mabartos.controller;

import org.mabartos.api.model.BartSession;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.model.UserModel;

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
import java.util.Set;

@Path("/devices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class DevicesResource {
    private static final String DEVICE_ID_NAME = "idDevice";
    private static final String DEVICE_ID = "/{" + DEVICE_ID_NAME + ":[\\d]+}";
    public static final String DEVICE_PATH = "/devices";

    private final BartSession session;

    @Inject
    public DevicesResource(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<DeviceModel> getAll() {
        return session.getActualRoom().getChildren();
    }

    @POST
    public DeviceModel createDevice(@Valid DeviceModel device) {
        device.setHome(session.getActualHome());
        device.setRoom(session.getActualRoom());
        return session.devices().create(device);
    }

    @POST
    @Path(DEVICE_ID + "/add")
    public DeviceModel addDeviceToRoom(@PathParam(DEVICE_ID_NAME) Long id) {
        DeviceModel device = session.devices().findByID(id);
        if (device != null && session.getActualRoom() != null) {
            device.setRoom(session.getActualRoom());
            session.getActualRoom().addChild(device);
            return session.devices().updateByID(id, device);
        }
        return null;
    }

    @Path(DEVICE_ID)
    public DeviceResource forwardToDevice(@PathParam(DEVICE_ID_NAME) Long id) {
        if (session.getActualRoom() == null || ControllerUtil.containsItem(session.getActualRoom().getChildren(), id)) {
            return new DeviceResource(session.setActualDevice(id));
        }
        return null;
    }
}
