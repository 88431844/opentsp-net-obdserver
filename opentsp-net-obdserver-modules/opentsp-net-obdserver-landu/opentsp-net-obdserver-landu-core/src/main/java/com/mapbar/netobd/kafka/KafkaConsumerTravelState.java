package com.mapbar.netobd.kafka;

import java.util.Arrays;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mapbar.netobd.configuration.KafkaConfiguration;

import net.sf.json.JSONObject;

/**
 * @description: 消费行程开始 用途： 整合轨迹数据
 * @author xubh
 * @date: 2016年5月3日
 */
@Component
public class KafkaConsumerTravelState implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerTravelState.class);

	private boolean isRun = true;
	@Resource
	private KafkaConfiguration kafkaConfiguration;
	private KafkaConsumer<String, String> consumer;

	@Value("trip_state:obd_landu_vehicle_travel_start")
	private String trip_state;
	@Resource
	private KafkaOption kafkaOption;

	public KafkaConsumerTravelState() {

	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean run) {
		isRun = run;
	}

	public KafkaConsumerTravelState(String topic) {
		super();
		this.trip_state = topic;
	}

	@Override
	public void run() {
		KafkaConsumer<String, String> consumer = createConsumer();
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				JSONObject json = JSONObject.fromObject(record.value());
				logger.info("消费行程开始(点火)：" + trip_state + "上的数据:" + json);
				try {
					kafkaOption.optionTravelState(json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void close() {
		try {
			consumer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private KafkaConsumer<String, String> createConsumer() {
		Properties props = kafkaConfiguration.getConsumerProperties();
		this.consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList(this.trip_state));
		return consumer;
	}

	@PostConstruct
	public void begin() {
		logger.info("启动消费者");
		new Thread(this).start(); // 使用kafka集群中创建好的主题

	}

	public void end() {
		this.setRun(false);
	}

}
