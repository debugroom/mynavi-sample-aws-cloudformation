package org.debugroom.mynavi.sample.cloudformation.frontend.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClient;
import com.amazonaws.services.cloudformation.model.Export;
import com.amazonaws.services.cloudformation.model.ListExportsRequest;
import com.amazonaws.services.cloudformation.model.ListExportsResult;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.CloudFormationStackInfo;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.S3Info;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.SQSInfo;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.log.interceptor.MDCLoggingInterceptor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.aws.cache.config.annotation.CacheClusterConfig;
import org.springframework.cloud.aws.cache.config.annotation.EnableElastiCache;
import org.springframework.cloud.aws.context.config.annotation.EnableStackConfiguration;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.stream.Collectors;

@Profile("staging")
@EnableStackConfiguration(stackName = "mynavi-sample-infra-staging")
// @EnableElastiCache only For Cache, spring data session does not support.
// @EnableElastiCache({@CacheClusterConfig(name = "ElastiCacheRedis")})
@Configuration
public class StagingConfig {

    private static final String ALB_STACK_NAME = "ALBStagingStack";
    private static final String S3_STACK_NAME = "S3StagingStack";
    private static final String S3_BUCKET_EXPORT = "MynaviSampleS3Bucket-Staging";
    private static final String SQS_STACK_NAME = "SQSStagingStack";
    private static final String SQS_QUEUE_EXPORT = "MynaviSampleSQS-Staging";
    private static final String SQS_ENDPOINT_EXPORT = "MynaviSampleSQS-Staging-ServiceEndpoint";
    private static final String SQS_REGION_EXPORT = "MynaviSampleSQS-Staging-Region";
    private static final String ELASTICACHE_STACK_NAME = "ElastiCacheStagingStack";
    private static final String ELASTICACHE_ENDPOINT_EXPORT = "mynavi-sample-cloudformation-vpc-ElastiCacheRedisEndPoint-Staging";
    private static final String ELASTICACHE_PORT_EXPORT = "mynavi-sample-cloudformation-vpc-ElastiCacheRedisPort-Staging";

    @Bean
    CloudFormationStackInfo cloudFormationStackInfo(){
        return new CloudFormationStackInfo();
    }

    @Bean
    public RestOperations restOperations(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.rootUri(cloudFormationStackInfo().getExportValue(
              "ALBStagingStack" , "mynavi-sample-cloudformation-vpc-BackendALBDNS"))
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
