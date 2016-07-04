package com.mapbar.netobd;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.navinfo.opentsp.common.messaging.transport.amqp.configuration.AmqpConfiguration;
import com.navinfo.opentsp.common.messaging.transport.amqp.configuration.CommandChannelConfiguration;
import com.navinfo.opentsp.platform.configuration.SecurityApplicationConfiguration;


@SpringBootApplication
@EnableScheduling
@Import({SecurityApplicationConfiguration.class, AmqpConfiguration.class, CommandChannelConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setWebEnvironment(false);
        springApplication.run(args);
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService(){
        return new ScheduledThreadPoolExecutor(5);
    }
}
