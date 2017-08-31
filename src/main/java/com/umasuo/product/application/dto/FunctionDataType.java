package com.umasuo.product.application.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.umasuo.product.infrastructure.util.FunctionDataTypeUtils;

/**
 * 用于功能的数据类型。
 * 包括：boolean － 布尔型，value － 数值型，enum - 枚举型，string － 字符型。
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BooleanType.class, name = FunctionDataTypeUtils.BOOLEAN_TYPE),
    @JsonSubTypes.Type(value = ValueType.class, name = FunctionDataTypeUtils.VALUE_TYPE),
    @JsonSubTypes.Type(value = EnumType.class, name = FunctionDataTypeUtils.ENUM_TYPE),
    @JsonSubTypes.Type(value = StringType.class, name = FunctionDataTypeUtils.STRING_TYPE),
})
public interface FunctionDataType {

}
