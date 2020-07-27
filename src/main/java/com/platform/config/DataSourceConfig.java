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

/**
 *
 * Created by GaoJingFei on 2020/07/25.
 */

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
		dataSource.setJdbcUrl(DESUtil.decode(jdbcUrl) + "?serverTimezone=GMT%2B8&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
		dataSource.setUsername(DESUtil.decode(jdbcUsername));
		dataSource.setPassword(DESUtil.decode(jdbcPassword));
		dataSource.setMaximumPoolSize(3);//最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值
		dataSource.setConnectionTimeout(60000);//连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒
		dataSource.setMinimumIdle(1);//最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size
		dataSource.setIdleTimeout(500000);//空闲连接超时时间，默认值600000（10分钟），大于等于max-lifetime且max-lifetime>0，会被重置为0；不等于0且小于10秒，会被重置为10秒。
		dataSource.setMaxLifetime(Long.parseLong(maxLifetime));//连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短
		dataSource.setConnectionTestQuery("SELECT 1");//连接测试查询
		return dataSource;
	}
}
