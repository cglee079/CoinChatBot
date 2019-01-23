package com.cglee079.coinchatbot.config.mybatis;

import org.apache.ibatis.type.MappedTypes;

import com.cglee079.coinchatbot.config.id.CodeEnum;
import com.cglee079.coinchatbot.config.id.MenuState;

@MappedTypes(MenuState.class)
public class MenuStateEnumTypeHandler extends EnumTypeHandler {

    @Override
    CodeEnum from(String code) throws Exception {
		return CodeEnum.from(MenuState.values(), code);
    }

}
