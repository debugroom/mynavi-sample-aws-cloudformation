package org.debugroom.mynavi.sample.cloudformation.backend.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.CloudFormationStackInfo;
import org.springframework.cloud.aws.context.config.annotation.EnableStackConfiguration;
import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("staging")
@EnableStackConfiguration(stackName = "mynavi-sample-infra-staging")
@EnableRdsInstance(
        dbInstanceIdentifier = "mynavi-sample-cloudformation-staging-postgresql",
        password = "${rds.password}",
        readReplicaSupport = false)
@Configuration
public class StagingConfig {

    private static final String DYNAMODB_STACK_NAME = "DynamoDBStagingStack";
    private static final String DYNAMODB_ENDPOINT_EXPORT = "MynaviSampleDynamoDB-Staging-ServiceEndpoint";
    private static final String DYNAMODB_REGION_EXPORT = "MynaviSampleDynamoDB-Staging-Region";

    @Bean
    CloudFormationStackInfo cloudFormationStackInfo(){
        return new CloudFormationStackInfo();
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        return DynamoDBMapperConfig.builder()
                .withTableNameOverride(
                        DynamoDBMapperConfig.TableNameOverride
                                .withTableNamePrefix("staging_"))
                .build();
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