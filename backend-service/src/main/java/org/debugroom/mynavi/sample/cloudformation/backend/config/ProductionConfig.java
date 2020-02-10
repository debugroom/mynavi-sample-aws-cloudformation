package org.debugroom.mynavi.sample.cloudformation.backend.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClient;
import com.amazonaws.services.cloudformation.model.Export;
import com.amazonaws.services.cloudformation.model.ListExportsRequest;
import com.amazonaws.services.cloudformation.model.ListExportsResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.CloudFormationStackInfo;
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

    private static final String DYNAMODB_STACK_NAME = "DynamoDBProductionStack";
    private static final String DYNAMODB_ENDPOINT_EXPORT = "MynaviSampleDynamoDB-Production-ServiceEndpoint";
    private static final String DYNAMODB_REGION_EXPORT = "MynaviSampleDynamoDB-Production-Region";

    @Bean
    CloudFormationStackInfo cloudFormationStackInfo(){
        return new CloudFormationStackInfo();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                cloudFormationStackInfo().getExportValue(
                                        DYNAMODB_STACK_NAME, DYNAMODB_ENDPOINT_EXPORT),
                                cloudFormationStackInfo().getExportValue(
                                        DYNAMODB_STACK_NAME, DYNAMODB_REGION_EXPORT)))
                .build();
    }

}
