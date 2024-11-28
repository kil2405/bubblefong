package com.bubbleShooter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@PropertySources({ @PropertySource("classpath:application.properties"),
		@PropertySource("classpath:redis_url/${spring.server.mode}-application.properties"),
		@PropertySource("classpath:server_config/${spring.server.mode}-application.properties") })

public class RedisConfig {
	@Value("${redis.game.shard.info.host}")
	private String redisHostInfo;

	@Value("${redis.game.shard.info.port}")
	private String redisPortInfo;

	@Value("${redis.game.shard.info.name}")
	private String redisNameInfo;

	@Value("${redis.game.shard.info.password}")
	private String redisPasswordInfo;
	
	@Value("${spring.server.mode}")
	private String serverMode;
	
	@Primary
	@Bean
	public LettuceConnectionFactory gameRedisConnectionFactory() {
		if(!serverMode.equals("live"))
		{
			RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHostInfo, Integer.parseInt(redisPortInfo));
			redisStandaloneConfiguration.setHostName(redisHostInfo);
			redisStandaloneConfiguration.setPort(Integer.parseInt(redisPortInfo));
			redisStandaloneConfiguration.setPassword(redisPasswordInfo);
			LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);

			return lettuceConnectionFactory;
		}
		else
		{
			RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
			clusterConfiguration.clusterNode(redisHostInfo, Integer.parseInt(redisPortInfo));
			LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(clusterConfiguration);
			
			return lettuceConnectionFactory;
		}
	}

	@Bean
	public RedisTemplate<String, String> gameRedisTemplate(@Autowired @Qualifier("gameRedisConnectionFactory") LettuceConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.afterPropertiesSet();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());

		return redisTemplate;
	}
}
