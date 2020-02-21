package impl;

import models.capability.CapabilityModel;
import repository.CapabilityRepository;
import service.CapabilityService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CapabilityServiceImpl extends CRUDServiceImpl<CapabilityModel, CapabilityRepository> implements CapabilityService {

    @Inject
    CapabilityServiceImpl() {
        super(CapabilityRepository.class);
    }
}
