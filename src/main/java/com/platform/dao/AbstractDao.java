package com.platform.dao;

import lombok.Getter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by GaoJingFei on 2020/07/25.
 */

public abstract class AbstractDao extends SqlSessionDaoSupport{
	@Getter
	private String namespacePrefix = getClass().getName()+".";
	@Override
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
}
}
