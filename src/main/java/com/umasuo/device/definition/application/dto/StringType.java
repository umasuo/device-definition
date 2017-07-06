package com.umasuo.device.definition.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Davis on 17/7/5.
 */
@Data
public class StringType implements FunctionDataType, Serializable {

  private static final long serialVersionUID = -8338216552702594487L;

  private String type;

  public StringType() {
    this.type = STRING_TYPE;
  }
}
