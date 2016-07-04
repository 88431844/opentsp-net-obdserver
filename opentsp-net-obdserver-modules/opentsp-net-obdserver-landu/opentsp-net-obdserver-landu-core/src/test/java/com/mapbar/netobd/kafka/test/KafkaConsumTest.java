package com.mapbar.netobd.kafka.test;

import org.junit.Test;

import com.mapbar.netobd.kafka.KafkaCollisionAlert;
import com.mapbar.netobd.kafka.KafkaConsumerCarCondition;
import com.mapbar.netobd.kafka.KafkaConsumerCheckInfo;
import com.mapbar.netobd.kafka.KafkaConsumerTravelData;
import com.mapbar.netobd.kafka.KafkaConsumerTravelState;

public class KafkaConsumTest {
	/**
	 * 消费行碰撞提醒 用途：点火推送
	 */
	@Test
	public void testCollisionAlert() {
		// new KafkaCollisionAlert(PropertiesUtil.getPropertiesByKey("kafka.properties", "collision_alert")).start();// 使用kafka集群中创建好的主题 test
		// new KafkaCollisionAlert("obd_drive_detail_high").start();// 使用kafka集群中创建好的主题 test
		new Thread(new KafkaCollisionAlert("obd_landu_vehicle_alarm_info")).start();// 使用kafka集群中创建好的主题 test
	}

	/**
	 * 消费电压及实时位置 用途：点火推送
	 */
	public void testCarCondition() {
		new Thread(new KafkaConsumerCarCondition("obd_result_landu_car_condition")).start();// 使用kafka集群中创建好的主题 test
		// new KafkaConsumerCarCondition("obd_drive_detail_low").start();// 使用kafka集群中创建好的主题 test
	}

	/**
	 * 消费 体检数据 用途： 蓄电池电压
	 */
	public void testCheckInfo() {
		new Thread(new KafkaConsumerCheckInfo("obd_result_landu_check_info")).start();// 使用kafka集群中创建好的主题
	}

	/**
	 * 消费行程数据 用途： 整合轨迹数据
	 */
	public void testTravelData() {
		new Thread(new KafkaConsumerTravelData("obd_result_landu_travel_data2")).start(); // 使用kafka集群中创建好的主题
	}

	/**
	 * 消费行程开始 用途： 整合轨迹数据
	 */
	public void testTravelState() {
		new Thread(new KafkaConsumerTravelState("obd_landu_vehicle_travel_start")).start();// 使用kafka集群中创建好的主题
	}
}
