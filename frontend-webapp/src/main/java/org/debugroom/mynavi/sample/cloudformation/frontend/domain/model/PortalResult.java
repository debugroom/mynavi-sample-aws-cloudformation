package org.debugroom.mynavi.sample.cloudformation.frontend.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.debugroom.mynavi.sample.cloudformation.common.model.SampleResource;
import org.debugroom.mynavi.sample.cloudformation.common.model.UserResource;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PortalResult {

    List<SampleResource> sampleResourceList;
    List<UserResource> userResourceList;

}
