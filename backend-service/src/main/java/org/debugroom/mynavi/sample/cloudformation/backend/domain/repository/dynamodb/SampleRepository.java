package org.debugroom.mynavi.sample.cloudformation.backend.domain.repository.dynamodb;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import org.debugroom.mynavi.sample.cloudformation.backend.domain.model.dynamodb.SampleTable;
import org.debugroom.mynavi.sample.cloudformation.backend.domain.model.dynamodb.SampleTableKey;

@EnableScan
public interface SampleRepository extends CrudRepository<SampleTable, SampleTableKey> {
}
