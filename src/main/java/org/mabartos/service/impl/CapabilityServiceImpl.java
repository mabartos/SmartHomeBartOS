package org.mabartos.service.impl;

import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.repository.CapabilityRepository;
import org.mabartos.service.core.CapabilityService;

public class CapabilityServiceImpl extends CRUDServiceImpl<CapabilityModel, CapabilityRepository> implements CapabilityService {

    CapabilityServiceImpl(CapabilityRepository repository) {
        super(repository);
    }
}
