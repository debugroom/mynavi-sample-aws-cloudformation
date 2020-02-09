package org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SQSInfo {

    private String queueName;

}
