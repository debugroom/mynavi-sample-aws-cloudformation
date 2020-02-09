package org.debugroom.mynavi.sample.cloudformation.frontend.domain.service;

import org.debugroom.mynavi.sample.cloudformation.common.model.SampleResource;
import org.debugroom.mynavi.sample.cloudformation.common.model.UserResource;
import org.debugroom.mynavi.sample.cloudformation.frontend.domain.model.PortalResult;

import java.util.List;

public interface SampleOrchestrationService {

    public List<SampleResource> getSamples();
    public List<UserResource> getUsers();
    public PortalResult getPortalResult();


}