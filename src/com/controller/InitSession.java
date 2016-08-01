package com.controller;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class InitSession {

	private static SqlSession sqlSession = null;

	public static SqlSession getSqlSession() {

		if (sqlSession == null) {

			String res = "conf.xml";

			InputStream is = LoadPageController.class.getClassLoader()
					.getResourceAsStream(res);

			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
					.build(is);

			SqlSession sqlSession = sqlSessionFactory.openSession();
			return sqlSession;
		}
		return sqlSession;
	}

}
