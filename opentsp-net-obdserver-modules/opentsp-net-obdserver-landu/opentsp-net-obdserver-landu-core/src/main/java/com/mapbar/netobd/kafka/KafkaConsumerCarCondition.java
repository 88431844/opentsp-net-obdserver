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
 * 消费电压及实时位置 用途：点火推送
 * 
 * @author yulong
 *
 */
@Component
public class KafkaConsumerCarCondition implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerCarCondition.class);

	private boolean isRun = true;
	@Resource
	private KafkaConfiguration kafkaConfiguration;
	private KafkaConsumer<String, String> consumer;
	@Value("car_condition:obd_result_landu_car_condition")
	private String car_condition;
	@Resource
	private KafkaOption kafkaOption;

	public KafkaConsumerCarCondition() {

	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean run) {
		isRun = run;
	}

	public KafkaConsumerCarCondition(String topic) {
		super();
		this.car_condition = topic;
	}

	@Override
	public void run() {
		KafkaConsumer<String, String> consumer = createConsumer();
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				JSONObject json = JSONObject.fromObject(record.value());
				logger.info("消费车况" + car_condition + "上的数据:" + json);
				try {
					kafkaOption.optionCarCondition(json);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("消费轨迹点" + car_condition + "上的数据错误:" + json);
					continue;
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
		consumer.subscribe(Arrays.asList(this.car_condition));
		return consumer;
	}

	@PostConstruct
	public void begin() {
		new Thread(this).start(); // 使用kafka集群中创建好的主题
	}

	public void end() {
		this.setRun(false);
	}

}
