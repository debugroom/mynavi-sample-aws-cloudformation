package org.debugroom.mynavi.sample.cloudformation.backend.domain.service;

import org.debugroom.mynavi.sample.cloudformation.backend.domain.model.dynamodb.SampleTable;
import org.debugroom.mynavi.sample.cloudformation.backend.domain.model.jpa.entity.User;
import org.debugroom.mynavi.sample.cloudformation.common.model.SampleResource;

import java.util.List;

public interface SampleService {

    public List<User> getUsers();
    public List<SampleResource> getSamples();
    public SampleResource addSample();

}
