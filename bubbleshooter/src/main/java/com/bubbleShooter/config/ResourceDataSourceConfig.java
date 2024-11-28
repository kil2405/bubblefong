package com.bubbleShooter.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySources({ @PropertySource("classpath:application.properties"),
		@PropertySource("classpath:db_url/${spring.server.mode}-application.properties"),
		@PropertySource("classpath:server_config/${spring.server.mode}-application.properties") })

public class ResourceDataSourceConfig {
	private final String MYBATIS_CONFIG = "classpath:config/mybatisResourceConfig.xml";

	@Value("${spring.datasource.resource.url}")
	private String datasourceUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.resource")
	public DataSource resourceDataSource() throws SQLException {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setJdbcUrl(datasourceUrl);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
		hikariConfig.setConnectionTestQuery("SELECT 1");
		hikariConfig.setPoolName("ResourceDataSource");

		DataSource dataSource = new HikariDataSource(hikariConfig);
		return dataSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactoryResource(@Autowired @Qualifier("resourceDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		factoryBean.setConfigLocation(resolver.getResource(MYBATIS_CONFIG));

		return factoryBean.getObject();
	}

	@Bean
	public SqlSession sqlSessionResource(@Autowired @Qualifier("sqlSessionFactoryResource") SqlSessionFactory factory) {
		return new SqlSessionTemplate(factory);
	}
}
