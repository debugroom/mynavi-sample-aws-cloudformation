package org.debugroom.mynavi.sample.cloudformation.backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan("org.debugroom.mynavi.sample.cloudformation.backend.app.web")
@Configuration
public class MvcConfig implements WebMvcConfigurer {
}
