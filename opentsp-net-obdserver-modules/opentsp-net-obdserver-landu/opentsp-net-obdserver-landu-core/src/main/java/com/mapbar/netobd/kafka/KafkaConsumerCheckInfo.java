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
 * 消费 体检数据 用途： 蓄电池电压
 * 
 * @author yulong
 *
 */
@Component
public class KafkaConsumerCheckInfo implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerCheckInfo.class);

	private boolean isRun = true;
	@Resource
	private KafkaConfiguration kafkaConfiguration;
	private KafkaConsumer<String, String> consumer;

	@Value("check_info:obd_result_landu_check_info")
	private String check_info;
	@Resource
	private KafkaOption kafkaOption;

	public KafkaConsumerCheckInfo() {

	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean run) {
		isRun = run;
	}

	public KafkaConsumerCheckInfo(String topic) {
		super();
		this.check_info = topic;
	}

	@Override
	public void run() {
		KafkaConsumer<String, String> consumer = createConsumer();
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				JSONObject json = JSONObject.fromObject(record.value());
				logger.info("消费体检数据" + check_info + "上的数据:" + json);
				try {
					kafkaOption.optionCheckInfo(json);
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
		consumer.subscribe(Arrays.asList(this.check_info));
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
