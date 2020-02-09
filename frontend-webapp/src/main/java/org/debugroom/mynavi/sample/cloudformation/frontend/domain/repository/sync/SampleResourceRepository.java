package org.debugroom.mynavi.sample.cloudformation.frontend.domain.repository.sync;

import org.debugroom.mynavi.sample.cloudformation.common.model.SampleResource;

import java.util.List;

public interface SampleResourceRepository {

    public List<SampleResource> findAll();


}
