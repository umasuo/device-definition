package com.umasuo.device.definition.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Davis on 17/7/5.
 */
@Data
public class ValueType implements FunctionDataType, Serializable {

  private static final long serialVersionUID = -5780098908699303181L;

  private String type;

  private Integer startValue;

  private Integer endValue;

  private Integer interval;

  private Integer multiple;

  private String unit;

  public ValueType() {
    this.type = VALUE_TYPE;
  }
}
