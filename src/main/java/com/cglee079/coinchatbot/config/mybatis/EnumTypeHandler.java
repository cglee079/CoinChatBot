package com.cglee079.coinchatbot.config.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.cglee079.coinchatbot.config.id.CodeEnum;

public abstract class EnumTypeHandler implements TypeHandler<CodeEnum> {

	@Override
	public void setParameter(PreparedStatement ps, int i, CodeEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getCode());
	}

	@Override
	public CodeEnum getResult(ResultSet rs, String columnName) throws SQLException {
		String code = rs.getString(columnName);

		return getTypeByCodeWithCatch(code);
	}

	@Override
	public CodeEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
		String code = rs.getString(columnIndex);

		return getTypeByCodeWithCatch(code);
	}

	@Override
	public CodeEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String code = cs.getString(columnIndex);

		return getTypeByCodeWithCatch(code);
	}

	// Exception 발생시 처리
	private CodeEnum getTypeByCodeWithCatch(String code) {
		CodeEnum codeEnum = null;

		try {
			codeEnum = from(code);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return codeEnum;
	}

	// 각 Enum 타입 핸들러 마다 구현해야 할 메소드
	abstract CodeEnum from(String code) throws Exception;

}
