package org.debugroom.mynavi.sample.cloudformation.frontend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.debugroom.mynavi.sample.cloudformation.frontend.domain.model.Sample;
import org.debugroom.mynavi.sample.cloudformation.frontend.domain.repository.async.SampleRepository;
@Service
public class SampleCoreographyServiceImpl implements SampleCoreographyService{

    @Autowired
    SampleRepository sampleRepository;

    @Override
    public void save(Sample sample) {
        sampleRepository.save(sample);
    }

}
