package org.mabartos.controller.home;

import org.mabartos.api.controller.home.HomeResource;
import org.mabartos.api.controller.home.HomesResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.persistence.model.HomeModel;

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
import java.util.logging.Logger;

@Path("/homes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class HomesResourceProvider implements HomesResource {
    public static Logger logger = Logger.getLogger(HomesResourceProvider.class.getName());

    private BartSession session;

    @Inject
    public HomesResourceProvider(BartSession session) {
        this.session = session;
        this.session.initEnvironment();
    }

    @GET
    public Set<HomeModel> getAll() {
        return (session.getActualUser() != null) ? session.getActualUser().getHomes() : session.services().homes().getAll();
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
            session.getActualUser().addHome(home);
            return session.services().homes().updateByID(id, home);
        }
        return null;
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
