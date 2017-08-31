package com.umasuo.product.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.umasuo.product.infrastructure.util.FunctionDataTypeUtils;

import lombok.ToString;

import java.io.Serializable;

/**
 * 布尔类型，用于Function。
 */
@ToString
public final class BooleanType implements FunctionDataType, Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -4939166400519284908L;

  /**
   * 具体类型，在对象创建时会初始化为：boolean。
   */
  private String type;

  /**
   * Private constructor.
   */
  private BooleanType() {
    this.type = FunctionDataTypeUtils.BOOLEAN_TYPE;
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

  /**
   * Build a BooleanType.
   *
   * @return BooleanType boolean type
   */
  @JsonCreator
  public static BooleanType build() {
    return new BooleanType();
  }
}
