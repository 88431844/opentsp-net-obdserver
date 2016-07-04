package com.mapbar.netobd.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

	@Value("${kafka.bootstrap.servers:xdatanode-18:9092,xdatanode-19:9092,xdatanode-20:9092}")
	private String boot_strap_servers;
	@Value("${kafka.key.serializer:org.apache.kafka.common.serialization.StringSerializer}")
	private String key_serializer;
	@Value("${kafka.value.serializer:org.apache.kafka.common.serialization.StringSerializer}")
	private String value_serializer;
	@Value("${kafka.acks:1}")
	private String acks;
	@Value("${kafka.batch.size:16384}")
	private String batch_size;
	@Value("${kafka.linger.ms:0}")
	private String linger_ms;
	@Value("${kafka.buffer.memory:33554432}")
	private String buffer_memory;

	@Value("${kafka.truststore.location:D:/githome/git/Ne_obd_server/src/main/resources/kafka_ssl/generate/kafka.client.truststore.jks}")
	private String truststore_location;
	@Value("${kafka.truststore.password:123456}")
	private String truststore_password;
	@Value("${kafka.keystore.location:D:/githome/git/Ne_obd_server/src/main/resources/kafka_ssl/generate/kafka.client.keystore.jks}")
	private String keystore_location;
	@Value("${kafka.keystore.password:123456}")
	private String keystore_password;
	@Value("${kafka.kye.password:123456}")
	private String key_password;

	@Value("${kafka.key.deserializer:org.apache.kafka.common.serialization.StringDeserializer}")
	private String key_deserializer;
	@Value("${kafka.value.deserializer:org.apache.kafka.common.serialization.StringDeserializer}")
	private String value_deserializer;
	@Value("${kafka.group.id:neobdWanGroupaaa}")
	private String group_id;
	@Value("${kafka.enable.auto.commit:true}")
	private String enable_auto_commit;
	@Value("${kafka.auto.commit.interval.ms:100}")
	private String auto_commit_interval_ms;
	@Value("${kafka.session.timeout.ms:30000}")
	private String session_timeout_ms;

	/**
	 * 生产者配置
	 * 
	 * @return
	 */
	@Bean(name = "procederProperties")
	public Properties getProcederProperties() {
		Properties props = new Properties();
		props.put("bootstrap.servers", boot_strap_servers);
		props.put("key.serializer", key_serializer);
		props.put("value.serializer", value_serializer);
		props.put("acks", acks);
		props.put("batch.size", batch_size);
		props.put("linger.ms", linger_ms);
		props.put("buffer.memory", buffer_memory);

		props.put("security.protocol", "SSL");
		props.put("ssl.truststore.location", truststore_location);
		props.put("ssl.truststore.password", truststore_password);
		props.put("ssl.keystore.location", keystore_location);
		props.put("ssl.keystore.password", keystore_password);
		props.put("ssl.key.password", key_password);

		return props;
	}

	/**
	 * 消费者配置
	 * 
	 * @return
	 */
	@Bean(name = "consumerProperties")
	public Properties getConsumerProperties() {
		Properties props = new Properties();
		props.put("bootstrap.servers", boot_strap_servers);
		props.put("group.id", group_id);
		props.put("enable.auto.commit", enable_auto_commit);
		props.put("auto.commit.interval.ms", auto_commit_interval_ms);
		props.put("session.timeout.ms", session_timeout_ms);
		props.put("key.deserializer", key_deserializer);
		props.put("value.deserializer", value_deserializer);

		props.put("security.protocol", "SSL");
		props.put("ssl.truststore.location", truststore_location);
		props.put("ssl.truststore.password", truststore_password);
		props.put("ssl.keystore.location", keystore_location);
		props.put("ssl.keystore.password", keystore_password);
		props.put("ssl.key.password", key_password);

		return props;
	}

}
