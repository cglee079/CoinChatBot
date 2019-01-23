package com.cglee079.coinchatbot.config.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.cglee079.coinchatbot.config.id.CodeEnum;

public class CodeEnumTypeHandler<E extends Enum<E> & CodeEnum> extends BaseTypeHandler<E> {

	private Class<E> type;

	public CodeEnumTypeHandler(Class<E> type) {
		if (type == null)
			throw new IllegalArgumentException("Type argument cannot be null");
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		if (jdbcType == null) {
			ps.setString(i, parameter.getCode());
		} else {
			ps.setObject(i, parameter.getCode(), jdbcType.TYPE_CODE);
		}
	}

	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String s = rs.getString(columnName);
		return getCodeEnum(type, s);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String s = rs.getString(columnIndex);
		return getCodeEnum(type, s);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String s = cs.getString(columnIndex);
		return getCodeEnum(type, s);
	}

	public static <T extends Enum<T> & CodeEnum> T getCodeEnum(Class<T> enumClass, String code) {
		return EnumSet.allOf(enumClass).stream().filter(type -> type.getCode().equals(code)).findFirst()
				.orElseGet(null);
	}
}
