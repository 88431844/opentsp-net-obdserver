package com.navinfo.opentsp.netobd.gateway.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.navinfo.opentsp.common.messaging.transport.amqp.configuration.AmqpConfiguration;
import com.navinfo.opentsp.common.messaging.transport.amqp.configuration.CommandChannelConfiguration;
import com.navinfo.opentsp.gateway.balancer.web.proxy.common.BalancerConfiguration;
import com.navinfo.opentsp.gateway.web.config.FrontendTokenServiceConfiguration;
import com.navinfo.opentsp.gateway.web.config.SecurityConfig;
import com.navinfo.opentsp.platform.configuration.EurekaClient;
import com.navinfo.opentsp.platform.configuration.RedisConfigurationFactory;
import com.navinfo.opentsp.platform.fs.frontend.FileStorageFrontendConfiguration;
import com.navinfo.opentspcore.common.cloud.service.AmqpConnectionFactoryConfig;
import com.navinfo.opentspcore.common.log.DiagnosticInfo;
import com.navinfo.opentspcore.common.log.LogConfiguration;
import com.navinfo.opentspcore.common.security.token.TokenValidatorConfiguration;
import com.navinfo.opentspcore.common.validator.CallbackValidator;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {FrontendTokenServiceConfiguration.class, TokenValidatorConfiguration.class,
        RedisConfigurationFactory.class, AmqpConnectionFactoryConfig.class,CallbackValidator.class, DiagnosticInfo.class})
@Import({ AmqpConfiguration.class, CommandChannelConfiguration.class,
        SecurityConfig.class, EurekaClient.class, FileStorageFrontendConfiguration.class,
        BalancerConfiguration.class, LogConfiguration.class})
/**
 * If you want to be in the local debugging ,please add
 *
 * VM options:-Dspring.profiles.active=local
 */
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
