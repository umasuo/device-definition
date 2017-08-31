package com.umasuo.product.application.dto;

import com.umasuo.product.infrastructure.util.FunctionDataTypeUtils;

import lombok.ToString;

import java.io.Serializable;

/**
 * 字符型，用于Function。
 */
@ToString
public class StringType implements FunctionDataType, Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -8338216552702594487L;

  /**
   * 具体类型，在对象创建时会初始化为：string。
   */
  private String type;


  /**
   * Instantiates a new String type.
   */
  public StringType() {
    this.type = FunctionDataTypeUtils.STRING_TYPE;
  }

  /**
   * Gets type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets type.
   *
   * @param type the type
   */
  public void setType(String type) {
    this.type = type;
  }
}
