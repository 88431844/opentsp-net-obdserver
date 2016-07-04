package com.mapbar.netobd.configuration;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.SerializationUtils;

@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}

	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		// Number of seconds before expiration. Defaults to unlimited (0)
		// cacheManager.setDefaultExpiration(10); // 设置key-value超时时间
		return cacheManager;
	}

	@Bean(name = "redistemplate")
	public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);

		redisTemplate.setValueSerializer(new RedisSerializer<Object>() {
			@Override
			public byte[] serialize(Object object) throws SerializationException {
				if (object == null) {
					return new byte[0];
				}
				if (!(object instanceof Serializable)) {
					throw new IllegalArgumentException("RedisSerializer.serialize requires a Serializable payload " + "but received an object of type [" + object.getClass().getName() + "]");
				}
				return SerializationUtils.serialize((Serializable) object);
			}

			@Override
			public Object deserialize(byte[] bytes) throws SerializationException {
				if (bytes == null || bytes.length == 0) {
					return null;
				}
				return SerializationUtils.deserialize(bytes);
			}
		});

		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}