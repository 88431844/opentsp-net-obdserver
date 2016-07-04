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
 * 消费行程数据 用途： 整合轨迹数据
 * 
 * @author yulong
 *
 */
@Component
public class KafkaConsumerTravelData implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerTravelData.class);

	private boolean isRun = true;
	@Resource
	private KafkaConfiguration kafkaConfiguration;
	private KafkaConsumer<String, String> consumer;

	@Value("travel_data:obd_result_landu_travel_data2")
	private String travel_data;
	@Resource
	private KafkaOption kafkaOption;

	public KafkaConsumerTravelData() {

	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean run) {
		isRun = run;
	}

	public KafkaConsumerTravelData(String topic) {
		super();
		this.travel_data = topic;
	}

	@Override
	public void run() {
		KafkaConsumer<String, String> consumer = createConsumer();
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				JSONObject json = JSONObject.fromObject(record.value());
				logger.info("消费行程数据：" + travel_data + "上的数据:" + json);
				try {
					kafkaOption.optionTravelData(json);
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
		this.consumer = new KafkaConsumer<String, String>(props, null, null);
		consumer.subscribe(Arrays.asList(this.travel_data));
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
