package org.debugroom.mynavi.sample.cloudformation.frontend.domain.model;

import org.debugroom.mynavi.sample.cloudformation.common.model.SampleResource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Sample implements SampleResource {

    private String samplePartitionKey;
    private String sampleSortKey;
    private String sampleText;

}
