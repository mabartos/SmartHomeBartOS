package org.mabartos.controller.device;

import org.mabartos.api.controller.device.DeviceResource;
import org.mabartos.api.controller.device.DevicesResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.persistence.model.DeviceModel;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class DevicesResourceProvider implements DevicesResource {
    private final BartSession session;

    public DevicesResourceProvider(BartSession session) {
        this.session = session;
        this.session.initEnvironment();
    }

    @GET
    public Set<DeviceModel> getAll() {
        return session.getActualRoom() != null ? session.getActualRoom().getChildren() : session.services().devices().getAll();
    }

    
    @GET
    @Path("test")
    public DeviceModel createDevice() {
        if (session.getActualHome() != null) {
            DeviceModel test = new DeviceModel("test");
            test.setHome(session.getActualHome());
            if (session.getActualRoom() != null) {
                test.setRoom(session.getActualRoom());
            }
            return session.services().devices().create(test);
        }
        return null;
    }

    @POST
    @Path(DEVICE_ID + "/add")
    public DeviceModel addDeviceToRoom(@PathParam(DEVICE_ID_NAME) Long id) {
        return session.services().devices().addDeviceToRoom(session.getActualRoom().getID(), id);
    }

    @POST
    @Path(DEVICE_ID + "/remove")
    public Response removeDeviceFromRoom(@PathParam(DEVICE_ID_NAME) Long id) {
        if (session.services().devices().removeDeviceFromRoom(session.getActualRoom().getID(), id)) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @Path(DEVICE_ID)
    public DeviceResource forwardToDevice(@PathParam(DEVICE_ID_NAME) Long id) {
        if (session.getActualRoom() == null || ControllerUtil.containsItem(session.getActualRoom().getChildren(), id)) {
            return new DeviceResourceProvider(session.setActualDevice(id));
        }
        return null;
    }
}
