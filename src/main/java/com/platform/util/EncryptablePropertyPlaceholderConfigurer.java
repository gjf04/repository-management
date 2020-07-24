package com.platform.util;

import com.gao.common.util.DESUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * 
 * 数据库配置信息解密
 * @author GaoJingFei
 *
 */
public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	public static final String JDBC_URL = "jdbc.url";
	public static final String JDBC_USERNAME = "jdbc.username";
	public static final String JDBC_PASSWORD = "jdbc.password";
	
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) throws BeansException {
		try{
            String url = properties.getProperty(JDBC_URL);
            if (url != null) {
            	properties.setProperty(JDBC_URL, DESUtil.decode(url));
            }
            
			String username = properties.getProperty(JDBC_USERNAME);
            if (username != null) {
            	properties.setProperty(JDBC_USERNAME, DESUtil.decode(username));
            }
            
            String password = properties.getProperty(JDBC_PASSWORD);
            if (password != null) {
            	properties.setProperty(JDBC_PASSWORD, DESUtil.decode(password));
            }
			super.processProperties(beanFactory, properties);
		}catch(Exception e){
			e.printStackTrace();
            throw new BeanInitializationException(e.getMessage());
		}
	}
}
