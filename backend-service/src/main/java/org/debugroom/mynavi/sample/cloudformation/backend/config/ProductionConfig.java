package org.debugroom.mynavi.sample.cloudformation.backend.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClient;
import com.amazonaws.services.cloudformation.model.Export;
import com.amazonaws.services.cloudformation.model.ListExportsRequest;
import com.amazonaws.services.cloudformation.model.ListExportsResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.stream.Collectors;

@Profile("production")
@EnableRdsInstance(
        dbInstanceIdentifier = "mynavi-sample-cloudformation-production-postgresql",
        password = "${rds.password}",
        readReplicaSupport = false)
@Configuration
public class ProductionConfig {

    @Autowired
    AmazonCloudFormationClient amazonCloudFormationClient;

    @Autowired(required = false)
    ResourceIdResolver resourceIdResolver;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        ListExportsResult listExportsResult = amazonCloudFormationClient
                .listExports(new ListExportsRequest());
        List<Export> exportList = listExportsResult.getExports();
        List<Export> dynamoDBStackExportList = exportList.stream()
                .filter(export -> export.getExportingStackId().equals(
                        resourceIdResolver.resolveToPhysicalResourceId("DynamoDBProductionStack")))
                .collect(Collectors.toList());
        Export dynamoDBServiceEndpointExport = dynamoDBStackExportList.stream()
                .filter(export -> export.getName().equals("MynaviSampleDynamoDB-Production-ServiceEndpoint"))
                .findFirst().get();
        Export environmentRegionExport = dynamoDBStackExportList.stream()
                .filter(export -> export.getName().equals("MynaviSampleDynamoDB-Production-Region"))
                .findFirst().get();
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                dynamoDBServiceEndpointExport.getValue(), environmentRegionExport.getValue()))
                .build();

    }

}
