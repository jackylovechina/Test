package com.domapper;

import org.apache.ibatis.session.SqlSession;

import com.domain.Test;

public class DoTest {

	public static int insertTest(SqlSession sqlSession, String stateMent,
			Test test) {
		// String stateMent = "com.mapping.testMapper.insertTest";
		int i = sqlSession.insert(stateMent, test);
		sqlSession.commit();

		return i;

	}

}
