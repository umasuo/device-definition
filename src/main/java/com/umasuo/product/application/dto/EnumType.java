package com.umasuo.product.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Davis on 17/7/5.
 */
@Data
public class EnumType implements FunctionDataType, Serializable {

  private static final long serialVersionUID = -5630734731563581589L;

  private String type;

  private List<String> values;

  public EnumType() {
    this.type = ENUM_TYPE;
  }
}
