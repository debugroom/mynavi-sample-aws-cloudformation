package org.debugroom.mynavi.sample.cloudformation.frontend.domain.repository.sync;

import java.util.List;

import org.debugroom.mynavi.sample.cloudformation.common.model.UserResource;

public interface UserResourceRepository {

    public List<UserResource> findAll();

}
