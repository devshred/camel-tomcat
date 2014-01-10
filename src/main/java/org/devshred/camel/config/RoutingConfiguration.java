package org.devshred.camel.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ComponentScan("org.devshred.camel.routes")
@Configuration
public class RoutingConfiguration {
}
