package org.mabartos.service.impl;

import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.repository.CapabilityRepository;
import org.mabartos.service.core.CapabilityService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CapabilityServiceImpl extends CRUDServiceImpl<CapabilityModel, CapabilityRepository> implements CapabilityService {

    @Inject
    CapabilityServiceImpl(CapabilityRepository repository) {
        super(repository);
    }
}
