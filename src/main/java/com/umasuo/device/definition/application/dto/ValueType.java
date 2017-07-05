package com.umasuo.device.definition.application.dto;

import lombok.Data;

/**
 * Created by Davis on 17/7/5.
 */
@Data
public class ValueType implements FunctionDataType {

  private String type;

  private Integer startValue;

  private Integer endValue;

  private Integer interval;

  private Integer multiple;

  private ValueType() {
    this.type = VALUE_TYPE;
  }
}
