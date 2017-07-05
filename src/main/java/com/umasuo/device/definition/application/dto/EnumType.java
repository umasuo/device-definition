package com.umasuo.device.definition.application.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Davis on 17/7/5.
 */
@Data
public class EnumType implements FunctionDataType {

  private String type;

  private List<String> values;

  public EnumType() {
    this.type = ENUM_TYPE;
  }
}
