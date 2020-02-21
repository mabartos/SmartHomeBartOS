package resources;

import models.DeviceModel;
import models.RoomModel;
import models.capability.CapabilityModel;
import service.CRUDService;
import service.DeviceService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Path("/devices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class DeviceResource {

    private static final String DEVICE_ID_NAME = "idDevice";
    private static final String DEVICE_ID = "/{" + DEVICE_ID_NAME + ":[\\d]+}";
    public static final String DEVICE_PATH = "/devices";
    public static final String CAPABILITY = DEVICE_ID + "/capability";

    private RoomModel parent;

    private DeviceService deviceService;

    @Inject
    public DeviceResource(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public DeviceResource(RoomModel parent, Set<CRUDService> services) {
        this.parent = parent;
        this.deviceService = (DeviceService) services.stream()
                .filter(f -> f instanceof DeviceService)
                .findFirst()
                .orElseThrow(NotFoundException::new);
        setParent();
    }

    @PostConstruct
    public void setParent() {
        if (this.parent != null)
            deviceService.setParentModel(this.parent);
    }

    @GET
    @Path(DEVICE_ID)
    public DeviceModel getDeviceByID(@PathParam(DEVICE_ID_NAME) Long id) {
        return deviceService.findByID(id);
    }

    @GET
    public Set<DeviceModel> getAll() {
        return deviceService.getAll();
    }

    @GET
    @Path(CAPABILITY)
    public List<CapabilityModel> getCapabilities(@PathParam(DEVICE_ID_NAME) Long id) {
        if (deviceService.exists(id)) {
            return deviceService.findByID(id).getCapabilities();
        } else {
            return null;
        }
    }

    @POST
    public DeviceModel createDevice(@Valid DeviceModel device) {
        return deviceService.create(device);
    }

    @POST
    @Path(DEVICE_ID)
    public boolean addDeviceToRoom(@PathParam(DEVICE_ID_NAME) Long idDevice) {
        return deviceService.addModelToParent(idDevice);
    }

    @PATCH
    @Path(DEVICE_ID)
    public DeviceModel updateDevice(@PathParam(DEVICE_ID_NAME) Long id, @Valid DeviceModel device) {
        return deviceService.updateByID(id, device);
    }

    @DELETE
    @Path(DEVICE_ID)
    public boolean deleteDevice(@PathParam(DEVICE_ID_NAME) Long id) {
        return deviceService.deleteByID(id);
    }
}
