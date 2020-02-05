package org.debugroom.mynavi.sample.cloudformation.frontend.config;

import com.amazonaws.services.cloudformation.AmazonCloudFormationClient;
import com.amazonaws.services.cloudformation.model.Export;
import com.amazonaws.services.cloudformation.model.ListExportsRequest;
import com.amazonaws.services.cloudformation.model.ListExportsResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.cache.config.annotation.CacheClusterConfig;
import org.springframework.cloud.aws.cache.config.annotation.EnableElastiCache;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

import java.util.List;
import java.util.stream.Collectors;

@Profile("staging")
@EnableElastiCache({@CacheClusterConfig(name = "ElastiCacheRedis")})
@Configuration
public class StagingConfig implements InitializingBean {

    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Autowired
    AmazonCloudFormationClient amazonCloudFormationClient;

    @Autowired(required = false)
    ResourceIdResolver resourceIdResolver;

    @Bean
    RedisConnectionFactory lettuceConnectionFactory(){
        ListExportsResult listExportsResult = amazonCloudFormationClient
                .listExports(new ListExportsRequest());
        List<Export> elastiCacheTestExportList = listExportsResult.getExports().stream()
                .filter(export -> export.getExportingStackId().equals(
                        resourceIdResolver.resolveToPhysicalResourceId("ElastiCacheStack")
                )).collect(Collectors.toList());
        Export elastiCacheEndpointExport = elastiCacheTestExportList.stream()
                .filter(export -> export.getName().equals(
                        "mynavi-sample-cloudformation-vpc-ElastiCacheRedisEndPoint-Staging"
                )).findFirst().get();
        Export elastiCachePortExport = elastiCacheTestExportList.stream()
                .filter(export -> export.getName().equals(
                        "mynavi-sample-cloudformation-vpc-ElastiCacheRedisPort-Staging"
                )).findFirst().get();
        return new LettuceConnectionFactory(elastiCacheEndpointExport.getValue(),
                Integer.parseInt(elastiCachePortExport.getValue()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Example for getting clooudformation export value. It is easy to get value from parent stack defined child stack.
        ListExportsResult listExportsResult = amazonCloudFormationClient
                .listExports(new ListExportsRequest());
        List<Export> exportList = listExportsResult.getExports();
        List<Export> albStackExportList = exportList.stream()
                .filter(export -> export.getExportingStackId().equals(
                        resourceIdResolver.resolveToPhysicalResourceId("ALBDevStack")))
                .collect(Collectors.toList());
        List<Export> rdsStackExportList = exportList.stream()
                .filter(export -> export.getExportingStackId().equals(
                        resourceIdResolver.resolveToPhysicalResourceId("RDSDevStack")))
                .collect(Collectors.toList());
        List<Export> dynamoDBStackExportList = exportList.stream()
                .filter(export -> export.getExportingStackId().equals(
                        resourceIdResolver.resolveToPhysicalResourceId("DynamoDBDevStack")))
                .collect(Collectors.toList());
        List<Export> elastiCacheStackExportList = exportList.stream()
                .filter(export -> export.getExportingStackId().equals(
                        resourceIdResolver.resolveToPhysicalResourceId("ElastiCacheDevStack")))
                .collect(Collectors.toList());
        List<Export> s3StackExportList = exportList.stream()
                .filter(export -> export.getExportingStackId().equals(
                        resourceIdResolver.resolveToPhysicalResourceId("S3DevStack")))
                .collect(Collectors.toList());
        List<Export> sqsStackExportList = exportList.stream()
                .filter(export -> export.getExportingStackId().equals(
                        resourceIdResolver.resolveToPhysicalResourceId("SQSDEvStack")))
                .collect(Collectors.toList());

    }
}
