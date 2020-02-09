package org.debugroom.mynavi.sample.cloudformation.backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.debugroom.mynavi.sample.cloudformation.backend.app.listener")
public class SQSConfig {
}
