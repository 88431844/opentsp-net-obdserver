package com.mapbar.netobd.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 同步数据计划任务
 * 
 * @author yulong
 *
 */
@Component
public class MyJob {
	// @Scheduled(cron="30 * * * * ?")
	@Scheduled(cron = "0 1 * * * ?")
	public void work() {
	}
}
