package org.debugroom.mynavi.sample.cloudformation.frontend.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.debugroom.mynavi.sample.cloudformation.common.model.SampleResource;
import org.debugroom.mynavi.sample.cloudformation.common.model.UserResource;
import org.debugroom.mynavi.sample.cloudformation.frontend.domain.model.PortalResult;
import org.debugroom.mynavi.sample.cloudformation.frontend.domain.repository.async.SampleRepository;
import org.debugroom.mynavi.sample.cloudformation.frontend.domain.repository.sync.SampleResourceRepository;
import org.debugroom.mynavi.sample.cloudformation.frontend.domain.repository.sync.UserResourceRepository;

@Service
public class SampleOrchestrationServiceImpl implements SampleOrchestrationService{

    @Autowired
    SampleResourceRepository sampleResourceRepository;

    @Autowired
    UserResourceRepository userResourceRepository;

    @Override
    public List<SampleResource> getSamples(){
        return sampleResourceRepository.findAll();
    }

    @Override
    public List<UserResource> getUsers() {
        return userResourceRepository.findAll();
    }

    @Override
    public PortalResult getPortalResult() {
        return PortalResult.builder()
                .userResourceList(getUsers())
                .sampleResourceList(getSamples())
                .build();
    }

}
