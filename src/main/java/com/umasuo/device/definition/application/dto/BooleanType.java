package com.umasuo.device.definition.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;

/**
 * Created by Davis on 17/7/5.
 */
public class BooleanType implements FunctionDataType, Serializable {

  private static final long serialVersionUID = -4939166400519284908L;

  private String type;

  private BooleanType() {
    this.type = BOOLEAN_TYPE;
  }

  @JsonCreator
  public static BooleanType build() {
    return new BooleanType();
  }
}
