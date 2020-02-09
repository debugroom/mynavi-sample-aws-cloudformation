package org.debugroom.mynavi.sample.cloudformation.backend.app.listener;

import org.debugroom.mynavi.sample.cloudformation.backend.domain.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableSqs
@Slf4j
public class MessageListener {

    @Autowired
    SampleService sampleService;

    @SqsListener(value = "SQSSampleQueue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void onMessage(String message){
       log.info(message);
       sampleService.addSample(message);
    }

}
