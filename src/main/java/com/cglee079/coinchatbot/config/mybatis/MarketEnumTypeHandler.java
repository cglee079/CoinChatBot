package com.cglee079.coinchatbot.config.mybatis;

import org.apache.ibatis.type.MappedTypes;

import com.cglee079.coinchatbot.config.id.CodeEnum;
import com.cglee079.coinchatbot.config.id.Market;

@MappedTypes(Market.class)
public class MarketEnumTypeHandler extends EnumTypeHandler {

    @Override
    CodeEnum from(String code) throws Exception {
		return CodeEnum.from(Market.values(), code);
    }

}
