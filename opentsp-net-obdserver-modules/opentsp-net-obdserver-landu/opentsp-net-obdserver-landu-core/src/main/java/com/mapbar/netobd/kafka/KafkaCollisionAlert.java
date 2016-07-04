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
 * 消费行碰撞提醒 用途：点火推送
 * 
 * @author yulong
 *
 */
@Component
public class KafkaCollisionAlert implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(KafkaCollisionAlert.class);

	private boolean isRun = true;
	@Resource
	private KafkaConfiguration kafkaConfiguration;
	private KafkaConsumer<String, String> consumer;

	@Value("collision_alert:obd_landu_vehicle_alarm_info")
	private String collision_alert;

	@Resource
	private KafkaOption kafkaOption;

	public KafkaCollisionAlert() {

	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean run) {
		isRun = run;
	}

	public KafkaCollisionAlert(String topic) {
		super();
		this.collision_alert = topic;
	}

	@Override
	public void run() {
		KafkaConsumer<String, String> consumer = createConsumer();
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				JSONObject json = JSONObject.fromObject(record.value());
				logger.info("消费碰撞、获取故障码和拔下OBD" + collision_alert + "上的数据:" + json);
				try {
					kafkaOption.optionCollisionAlert(json);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("消费碰撞和拔下OBD" + collision_alert + "上的数据错误:" + json);
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
		consumer.subscribe(Arrays.asList(this.collision_alert));
		return consumer;
	}

	@PostConstruct
	public void begin() {
		new Thread(this).start();// 使用kafka集群中创建好的主题
	}

	public void end() {
		this.setRun(false);
	}

}
