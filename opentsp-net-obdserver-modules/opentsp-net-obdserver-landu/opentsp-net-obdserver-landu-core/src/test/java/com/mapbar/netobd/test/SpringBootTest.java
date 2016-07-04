package com.mapbar.netobd.test;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.mapbar.netobd.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = false)
@WebIntegrationTest("server.port:0")
@ActiveProfiles(profiles = "local")
public class SpringBootTest {
	@Configuration
	public static class RestClientConfiguration {

		@Bean
		RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
			return new RestTemplate(Arrays.asList(hmc));
		}

		@Bean
		ProtobufHttpMessageConverter protobufHttpMessageConverter() {
			return new ProtobufHttpMessageConverter();
		}
	}

	@Autowired
	private WebApplicationContext context;
	@Value("${local.server.port}")
	private int port;
	private MockMvc mockMvc;
	private RestTemplate restTemplate = new TestRestTemplate();

	@Before
	public void setupMockMvc() {
		// 绑定需要测试的Controller到MockMvcshang
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void webappBookIsbnApi() {
		restTemplate.getForObject("http://localhost:" + port + "/books/9876-5432-1111", String.class);
		Assert.assertNotNull("");
	}

	@Test
	public void webappPublisherApi() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("url").accept(MediaType.APPLICATION_JSON))//
				.andExpect(MockMvcResultMatchers.status().isOk()).//
				andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("content")))//
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("中文测试"));
	}
}
