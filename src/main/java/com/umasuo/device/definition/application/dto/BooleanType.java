package com.umasuo.device.definition.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Created by Davis on 17/7/5.
 */
public class BooleanType implements FunctionDataType{

  private String type;

  private BooleanType() {
    this.type = BOOLEAN_TYPE;
  }

  @JsonCreator
  public static BooleanType build() {
    return new BooleanType();
  }
}
