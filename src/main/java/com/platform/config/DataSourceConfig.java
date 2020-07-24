package com.platform.config;

import com.gao.common.util.DESUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
	@Value("${jdbc.driver-class-name}")
	private String jdbcDriver;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	@Value("${jdbc.hikari.maxLifetime}")
	private String maxLifetime;

	@Bean(name = "dataSource")
	public DataSource createDataSourceBean() throws Exception {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(jdbcDriver);
		dataSource.setJdbcUrl(DESUtil.decode(jdbcUrl));
		dataSource.setUsername(DESUtil.decode(jdbcUsername));
		dataSource.setPassword(DESUtil.decode(jdbcPassword));
		dataSource.setMaxLifetime(Long.parseLong(maxLifetime));
		return dataSource;
	}
}
