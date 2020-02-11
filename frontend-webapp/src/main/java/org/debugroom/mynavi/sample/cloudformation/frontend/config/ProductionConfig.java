package org.debugroom.mynavi.sample.cloudformation.frontend.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.CloudFormationStackInfo;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.S3Info;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.SQSInfo;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.log.interceptor.MDCLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.web.client.RestOperations;

@Profile("production")
// @EnableElastiCache only For Cache, spring data session does not support.
// @EnableElastiCache({@CacheClusterConfig(name = "ElastiCacheRedis")})
@Configuration
public class ProductionConfig {

    private static final String PROTOCOL = "https://";
    private static final String ALB_STACK_NAME = "ALBProductionStack";
    private static final String ALB_BACKEND_ALB_EXPORT_NAME = "mynavi-sample-cloudformation-vpc-BackendALBDNS-Production";
    private static final String S3_STACK_NAME = "S3ProductionStack";
    private static final String S3_BUCKET_EXPORT = "MynaviSampleS3Bucket-Production";
    private static final String SQS_STACK_NAME = "SQSProductionStack";
    private static final String SQS_QUEUE_EXPORT = "MynaviSampleSQS-Production";
    private static final String SQS_ENDPOINT_EXPORT = "MynaviSampleSQS-Production-ServiceEndpoint";
    private static final String SQS_REGION_EXPORT = "MynaviSampleSQS-Production-Region";
    private static final String ELASTICACHE_STACK_NAME = "ElastiCacheProductionStack";
    private static final String ELASTICACHE_ENDPOINT_EXPORT = "mynavi-sample-cloudformation-vpc-ElastiCacheRedisEndPoint-Production";
    private static final String ELASTICACHE_PORT_EXPORT = "mynavi-sample-cloudformation-vpc-ElastiCacheRedisPort-Production";

    @Bean
    CloudFormationStackInfo cloudFormationStackInfo(){
        return new CloudFormationStackInfo();
    }

    @Bean
    public RestOperations restOperations(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.rootUri(PROTOCOL + cloudFormationStackInfo().getExportValue(
                ALB_STACK_NAME , ALB_BACKEND_ALB_EXPORT_NAME))
                .interceptors(new MDCLoggingInterceptor()).build();
    }

    @Bean
    S3Info s3Info(){
        return S3Info.builder()
                .bucketName(cloudFormationStackInfo()
                        .getExportValue(S3_STACK_NAME, S3_BUCKET_EXPORT))
                .build();
    }

    @Bean
    SQSInfo sqsInfo(){
        return SQSInfo.builder()
                .queueName(cloudFormationStackInfo().getExportValue(
                        SQS_STACK_NAME, SQS_QUEUE_EXPORT))
                .build();
    }

    @Autowired
    AmazonSQSAsync amazonSQSAsync;

    @Bean
    public AwsClientBuilder.EndpointConfiguration endpointConfiguration(){
        return new AwsClientBuilder.EndpointConfiguration(
                cloudFormationStackInfo().getExportValue(
                        SQS_STACK_NAME, SQS_ENDPOINT_EXPORT),
                cloudFormationStackInfo().getExportValue(
                        SQS_STACK_NAME, SQS_REGION_EXPORT));
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(){
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(){
        return new LettuceConnectionFactory(
                cloudFormationStackInfo().getExportValue(
                        ELASTICACHE_STACK_NAME, ELASTICACHE_ENDPOINT_EXPORT),
                Integer.valueOf(cloudFormationStackInfo().getExportValue(
                        ELASTICACHE_STACK_NAME, ELASTICACHE_PORT_EXPORT)));
    }

}
