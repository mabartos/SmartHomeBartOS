package org.mabartos.controller.home;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.model.BartSession;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.persistence.model.HomeModel;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
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
import java.util.logging.Logger;

@Path("/homes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class HomesResource {
    public static Logger logger = Logger.getLogger(HomesResource.class.getName());

    public static final String HOME_ID_NAME = "idHome";
    public static final String HOME_ID = "/{" + HOME_ID_NAME + ":[\\d]+}";
    public static final String HOME_PATH = "/homes";

    private BartSession session;

    @Inject
    public HomesResource(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<HomeModel> getAll() {
        return (session.getActualUser() != null) ? session.getActualUser().getChildren() : session.services().homes().getAll();
    }

    @POST
    public HomeModel createHome(@Valid HomeModel home) {
        return session.services().homes().create(home);
    }

    @POST
    @Path(HOME_ID + "/add")
    public HomeModel addHomeToUser(@PathParam(HOME_ID_NAME) Long id) {
        HomeModel home = session.services().homes().findByID(id);
        if (home != null && session.getActualUser() != null) {
            home.addUser(session.getActualUser());
            session.getActualUser().addChild(home);
            return session.services().homes().updateByID(id, home);
        }
        return null;
    }

    @Path(HOME_ID)
    public HomeResource forwardToHome(@PathParam(HOME_ID_NAME) Long id) {
        if (session.getActualUser() == null || ControllerUtil.containsItem(session.getActualUser().getChildren(), id)) {
            return new HomeResource(session.setActualHome(id));
        }
        return null;
    }
}
