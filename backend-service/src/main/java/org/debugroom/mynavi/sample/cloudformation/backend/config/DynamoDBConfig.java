package org.debugroom.mynavi.sample.cloudformation.backend.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;

import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(
        basePackages = "org.debugroom.mynavi.sample.cloudformation.backend.domain.repository.dynamodb"
)
public class DynamoDBConfig {
}
