package com.mapbar.netobd.configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navinfo.opentsp.common.messaging.MessageChannel;
import com.navinfo.opentsp.common.messaging.transport.amqp.RabbitMQ;
import com.navinfo.opentsp.platform.configuration.AbstractCommandListenerConfiguration;
import com.navinfo.opentsp.scheduler.commands.builder.SchedulerAPIService;
import com.navinfo.opentspcore.common.CoreCommandHandler;

/**
 * listener configuration
 *
 * @author xubh
 */
@Configuration
@ComponentScan(basePackageClasses = { CoreCommandHandler.class })
@RabbitMQ
public class CommandListenerConfiguration extends AbstractCommandListenerConfiguration {

	@Autowired
	private MessageChannel messageChannel;

	@Bean
	@Qualifier(value = "command")
	@RabbitMQ
	public Queue commandQueue(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue("net_obd_landu", false, false, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	public ScheduledExecutorService scheduledExecutorService() {
		return new ScheduledThreadPoolExecutor(5);
	}

	@Bean
	public SchedulerAPIService schedulerAPIService() {
		return new SchedulerAPIService(new ObjectMapper(), messageChannel);
	}

}
