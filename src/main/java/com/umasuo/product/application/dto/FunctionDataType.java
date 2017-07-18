package com.umasuo.product.application.dto;

/**
 * Created by Davis on 17/7/5.
 */

import static com.umasuo.product.application.dto.FunctionDataType.BOOLEAN_TYPE;
import static com.umasuo.product.application.dto.FunctionDataType.ENUM_TYPE;
import static com.umasuo.product.application.dto.FunctionDataType.STRING_TYPE;
import static com.umasuo.product.application.dto.FunctionDataType.VALUE_TYPE;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BooleanType.class, name = BOOLEAN_TYPE),
    @JsonSubTypes.Type(value = ValueType.class, name = VALUE_TYPE),
    @JsonSubTypes.Type(value = EnumType.class, name = ENUM_TYPE),
    @JsonSubTypes.Type(value = StringType.class, name = STRING_TYPE),
})
public interface FunctionDataType {

  String BOOLEAN_TYPE = "boolean";

  String VALUE_TYPE = "value";

  String ENUM_TYPE = "enum";

  String STRING_TYPE = "string";
}
