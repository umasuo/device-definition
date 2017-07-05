package com.umasuo.device.definition.application.dto;

import lombok.Data;

/**
 * Created by Davis on 17/7/5.
 */
@Data
public class StringType implements FunctionDataType {

  private String type;

  private StringType() {
    this.type = STRING_TYPE;
  }
}
