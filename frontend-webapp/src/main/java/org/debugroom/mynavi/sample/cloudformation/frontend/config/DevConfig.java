package org.debugroom.mynavi.sample.cloudformation.frontend.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.aws.context.config.annotation.EnableStackConfiguration;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.web.client.RestOperations;

import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.S3Info;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.SQSInfo;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.CloudFormationStackInfo;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.log.interceptor.MDCLoggingInterceptor;

@Profile("dev")
@EnableStackConfiguration(stackName = "mynavi-sample-infra-dev")
@Configuration
public class DevConfig {

    private static final String BACKEND_SERVICE_DNS = "http://localhost:8080";

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public RestOperations restOperations(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.rootUri(BACKEND_SERVICE_DNS)
                .interceptors(new MDCLoggingInterceptor()).build();
    }

    @Bean
    S3Info s3Info(){
        return S3Info.builder()
                .bucketName(cloudFormationStackInfo().getExportValue(
                        "S3DevStack", "MynaviSampleS3Bucket-Dev"))
                .build();
    }

    @Bean
    SQSInfo sqsInfo(){
        return SQSInfo.builder()
                .queueName(cloudFormationStackInfo().getExportValue(
                        "SQSDevStack", "MynaviSampleSQS-Dev"))
                .build();
    }

    @Autowired
    AmazonSQSAsync amazonSQSAsync;

    @Bean
    public AwsClientBuilder.EndpointConfiguration endpointConfiguration(){
        return new AwsClientBuilder.EndpointConfiguration(
                cloudFormationStackInfo().getExportValue(
                        "SQSDevStack", "MynaviSampleSQS-Dev-ServiceEndpoint"), region);
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(){
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Bean
    CloudFormationStackInfo cloudFormationStackInfo(){
        return new CloudFormationStackInfo();
    }


    /*
    * ElasitiCache is not permitted public access, use local redis server except dev environment in vpc.
    */
    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(){
        return new LettuceConnectionFactory(
                cloudFormationStackInfo().getExportValue(
     "ElastiCacheDevStack", "mynavi-sample-cloudformation-vpc-ElastiCacheRedisEndPoint-Dev"),
                Integer.valueOf(cloudFormationStackInfo().getExportValue(
     "ElastiCacheDevStack", "mynavi-sample-cloudformation-vpc-ElastiCacheRedisPort-Dev")));
    }

}
