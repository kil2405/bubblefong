package com.bubbleShooter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

public class RankRedisConfig {

	@Value("${redis.rank.info.host}")
	private String rankRedisHostInfo;

	@Value("${redis.rank.info.port}")
	private String rankRedisPortInfo;

	@Value("${redis.rank.info.password}")
	private String rankRedisPasswordInfo;
	
	@Value("${spring.server.mode}")
	private String serverMode;

	@Bean
	public LettuceConnectionFactory rankRedisConnectionFactory() {
		if(!serverMode.equals("live"))
		{
			RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(rankRedisHostInfo,
					Integer.parseInt(rankRedisPortInfo));
			redisStandaloneConfiguration.setHostName(rankRedisHostInfo);
			redisStandaloneConfiguration.setPort(Integer.parseInt(rankRedisPortInfo));
			redisStandaloneConfiguration.setPassword(rankRedisPasswordInfo);
			LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);

			return lettuceConnectionFactory;
		}
		else
		{
			RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
			clusterConfiguration.clusterNode(rankRedisHostInfo, Integer.parseInt(rankRedisPortInfo));
			LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(clusterConfiguration);
			return lettuceConnectionFactory;
		}
		
	}

	@Bean
	public RedisTemplate<String, String> rankRedisTemplate(
			@Autowired @Qualifier("rankRedisConnectionFactory") LettuceConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.afterPropertiesSet();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());

		return redisTemplate;
	}
}
