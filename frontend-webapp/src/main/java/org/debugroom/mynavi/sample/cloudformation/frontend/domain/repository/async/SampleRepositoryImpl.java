package org.debugroom.mynavi.sample.cloudformation.frontend.domain.repository.async;

import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.SQSInfo;
import org.debugroom.mynavi.sample.cloudformation.frontend.domain.model.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SampleRepositoryImpl implements SampleRepository{

    @Autowired
    SQSInfo sqsInfo;

    @Autowired
    QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void save(Sample sample) {
        queueMessagingTemplate.convertAndSend(sqsInfo.getQueueName(),
                sample.getSampleText());
    }

}
