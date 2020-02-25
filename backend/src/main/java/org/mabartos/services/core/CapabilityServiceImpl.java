package org.mabartos.services.core;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.repository.CapabilityRepository;
import org.mabartos.api.service.CapabilityService;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Dependent
public class CapabilityServiceImpl extends CRUDServiceImpl<CapabilityModel, CapabilityRepository> implements CapabilityService {

    @Inject
    CapabilityServiceImpl(CapabilityRepository repository) {
        super(repository);
    }

    public void start(@Observes StartupEvent event) {
    }
}
