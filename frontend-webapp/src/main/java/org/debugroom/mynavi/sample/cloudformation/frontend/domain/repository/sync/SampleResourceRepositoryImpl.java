package org.debugroom.mynavi.sample.cloudformation.frontend.domain.repository.sync;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import org.debugroom.mynavi.sample.cloudformation.common.model.SampleResource;
import org.debugroom.mynavi.sample.cloudformation.frontend.domain.model.Sample;

@Component
public class SampleResourceRepositoryImpl implements SampleResourceRepository {

    private static final String SERVICE_NAME = "/backend";
    private static final String API_VERSION = "/api/v1";

    @Autowired
    RestOperations restOperations;

    @Override
    public List<SampleResource> findAll() {
        String endpoint = SERVICE_NAME + API_VERSION + "/samples";
        return Arrays.asList(restOperations.getForObject(
                endpoint, Sample[].class));
    }

}
