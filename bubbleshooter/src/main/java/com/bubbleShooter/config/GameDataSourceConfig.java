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
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySources({ @PropertySource("classpath:application.properties"),
		@PropertySource("classpath:db_url/${spring.server.mode}-application.properties"),
		@PropertySource("classpath:server_config/${spring.server.mode}-application.properties") })

public class GameDataSourceConfig {
	private final String MYBATIS_CONFIG = "classpath:config/mybatisGameConfig.xml";

	@Value("${spring.datasource.game0.url}")
	private String game00_url;

//	@Value("${spring.datasource.game1.url}")
//	private String game01_url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.game0")
	public DataSource mysql0DataSource() throws SQLException {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setJdbcUrl(game00_url);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
		hikariConfig.setConnectionTestQuery("SELECT 1");
		hikariConfig.setConnectionTimeout(10000);
		hikariConfig.setMaxLifetime(580000);
		hikariConfig.setConnectionInitSql("set wait_timeout = 600");
		hikariConfig.setPoolName("Game00DataSource");

		DataSource dataSource = new HikariDataSource(hikariConfig);
		return dataSource;
	}

//	@Bean
//	@ConfigurationProperties(prefix = "spring.datasource.game1")
//	public DataSource mysql1DataSource() throws SQLException {
//		HikariConfig hikariConfig = new HikariConfig();
//		hikariConfig.setDriverClassName(driverClassName);
//		hikariConfig.setJdbcUrl(game01_url);
//		hikariConfig.setUsername(username);
//		hikariConfig.setPassword(password);
//		hikariConfig.setMaximumPoolSize(10);
//		hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
//		hikariConfig.setConnectionTestQuery("SELECT 1");
//		hikariConfig.setPoolName("Game01DataSource");
//
//		DataSource dataSource = new HikariDataSource(hikariConfig);
//		return dataSource;
//	}

	@Bean
	@Primary
	public SqlSessionFactory sqlSessionFactory0(@Autowired @Qualifier("mysql0DataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		factoryBean.setConfigLocation(resolver.getResource(MYBATIS_CONFIG));

		return factoryBean.getObject();
	}

	@Bean
	@Primary
	public SqlSession sqlSession0(@Autowired @Qualifier("sqlSessionFactory0") SqlSessionFactory factory) {
		return new SqlSessionTemplate(factory);
	}

//	@Bean
//	public SqlSessionFactory sqlSessionFactory1(@Autowired @Qualifier("mysql1DataSource") DataSource dataSource) throws Exception {
//		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//		factoryBean.setDataSource(dataSource);
//		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//
//		factoryBean.setConfigLocation(resolver.getResource(MYBATIS_CONFIG));
//
//		return factoryBean.getObject();
//	}
//
//	@Bean
//	public SqlSession sqlSession1(@Autowired @Qualifier("sqlSessionFactory1") SqlSessionFactory factory) {
//		return new SqlSessionTemplate(factory);
//	}
}
