package org.debugroom.mynavi.sample.cloudformation.frontend.config;

import com.amazonaws.services.cloudformation.AmazonCloudFormationClient;
import com.amazonaws.services.cloudformation.model.Export;
import com.amazonaws.services.cloudformation.model.ListExportsRequest;
import com.amazonaws.services.cloudformation.model.ListExportsResult;
import org.debugroom.mynavi.sample.cloudformation.common.apinfra.log.interceptor.MDCLoggingInterceptor;
import org.debugroom.mynavi.sample.cloudformation.frontend.app.model.Sample;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.aws.cache.config.annotation.CacheClusterConfig;
import org.springframework.cloud.aws.cache.config.annotation.EnableElastiCache;
import org.springframework.cloud.aws.context.config.annotation.EnableStackConfiguration;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.stream.Collectors;

@Profile("dev")
@EnableStackConfiguration(stackName = "mynavi-sample-infra-dev")
@Configuration
public class DevConfig implements InitializingBean {

    private static final String BACKEND_SERVICE_DNS = "http://localhost:8080/backend";

    @Autowired
    AmazonCloudFormationClient amazonCloudFormationClient;

    @Autowired(required = false)
    ResourceIdResolver resourceIdResolver;

    @Bean
    public RestOperations restOperations(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.rootUri(BACKEND_SERVICE_DNS)
                .interceptors(new MDCLoggingInterceptor()).build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Example for getting clooudformation export value. It is easy to get value from parent stack defined child stack.
        ListExportsResult listExportsResult = amazonCloudFormationClient
                .listExports(new ListExportsRequest());
        List<Export> exportList = listExportsResult.getExports();
    }

}
