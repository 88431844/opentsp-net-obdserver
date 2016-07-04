package com.mapbar.netobd.configuration;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

/**
 * @description: MyBatis 配置
 * @author xubh
 * @date: 2016年4月28日
 */
@Configuration
@MapperScan(value = { "com.mapbar.netobd.mapper" })
public class MyBatisConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(MyBatisConfiguration.class);

	@Bean
	public PageHelper pageHelper() {
		logger.info("注册MyBatis分页插件PageHelper");
		PageHelper pageHelper = new PageHelper();
		Properties p = new Properties();
		// 该参数默认为false
		// 设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
		// 和startPage中的pageNum效果一样
		p.setProperty("offsetAsPageNum", "true");
		// 该参数默认为false
		// 设置为true时，使用RowBounds分页会进行count查询
		p.setProperty("rowBoundsWithCount", "true");
		// 设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果
		// （相当于没有执行分页查询，但是返回结果仍然是Page类型）
		// p.setProperty("pageSizeZero", "true");
		// 启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
		// 禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据
		p.setProperty("reasonable", "true");
		pageHelper.setProperties(p);
		return pageHelper;
	}

}