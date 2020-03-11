package org.mabartos.controller.device;

import org.mabartos.api.controller.device.DeviceResource;
import org.mabartos.api.controller.device.DevicesResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.DeviceModel;

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
public class DevicesResourceProvider implements DevicesResource {
    private final BartSession session;

    @Inject
    public DevicesResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<DeviceModel> getAll() {
        return session.getActualRoom() != null ? session.getActualRoom().getChildren() : session.services().devices().getAll();
    }

    @GET
    @Path("/caps")
    public Set<CapabilityModel> getCaps() {
        return session.services().capabilities().getAll();
    }

    @POST
    public DeviceModel createDevice(@Valid DeviceModel device) {
        if (session.getActualHome() != null) {
            device.setHome(session.getActualHome());
            if (session.getActualRoom() != null) {
                device.setRoom(session.getActualRoom());
            }
            return session.services().devices().create(device);
        }
        return null;
    }

    @POST
    @Path(DEVICE_ID + "/add")
    public DeviceModel addDeviceToRoom(@PathParam(DEVICE_ID_NAME) Long id) {
        DeviceModel device = session.services().devices().findByID(id);
        if (device != null && session.getActualRoom() != null) {
            device.setRoom(session.getActualRoom());
            session.getActualRoom().addChild(device);
            return session.services().devices().updateByID(id, device);
        }
        return null;
    }

    @Path(DEVICE_ID)
    public DeviceResource forwardToDevice(@PathParam(DEVICE_ID_NAME) Long id) {
        if (session.getActualRoom() == null || ControllerUtil.containsItem(session.getActualRoom().getChildren(), id)) {
            return new DeviceResourceProvider(session.setActualDevice(id));
        }
        return null;
    }
}
