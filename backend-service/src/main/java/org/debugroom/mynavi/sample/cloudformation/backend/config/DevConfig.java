package org.debugroom.mynavi.sample.cloudformation.backend.config;

import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClient;
import com.amazonaws.services.cloudformation.model.Export;
import com.amazonaws.services.cloudformation.model.ListExportsRequest;
import com.amazonaws.services.cloudformation.model.ListExportsResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.context.config.annotation.EnableStackConfiguration;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@EnableStackConfiguration(stackName = "mynavi-sample-infra-dev")
@EnableRdsInstance(
        dbInstanceIdentifier = "mynavi-sample-cloudformation-dev-postgresql",
        password = "${rds.password}",
        readReplicaSupport = false)
@Configuration
public class DevConfig implements InitializingBean {

    @Autowired
    AmazonCloudFormationClient amazonCloudFormationClient;

    @Autowired(required = false)
    ResourceIdResolver resourceIdResolver;

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig(){
        return DynamoDBMapperConfig.builder()
                .withTableNameOverride(
                        DynamoDBMapperConfig.TableNameOverride
                                .withTableNamePrefix("dev_"))
                .build();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB(){
        ListExportsResult listExportsResult = amazonCloudFormationClient
                .listExports(new ListExportsRequest());
        List<Export> exportList = listExportsResult.getExports();
        List<Export> dynamoDBStackExportList = exportList.stream()
                .filter(export -> export.getExportingStackId().equals(
                        resourceIdResolver.resolveToPhysicalResourceId("DynamoDBDevStack")))
                .collect(Collectors.toList());
        Export dynamoDBServiceEndpointExport = dynamoDBStackExportList.stream()
                .filter(export -> export.getName().equals("MynaviSampleDynamoDB-Dev-ServiceEndpoint"))
                .findFirst().get();
        Export environmentRegionExport = dynamoDBStackExportList.stream()
                .filter(export -> export.getName().equals("MynaviSampleDynamoDB-Dev-Region"))
                .findFirst().get();
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(
                        dynamoDBServiceEndpointExport.getValue(), environmentRegionExport.getValue()))
                .build();
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
