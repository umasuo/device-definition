package com.umasuo.product.application.dto;

import com.umasuo.product.infrastructure.util.FunctionDataTypeUtils;

import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 用于功能的数据类型，enum的具体实现。
 */
@ToString
public class EnumType implements FunctionDataType, Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -5630734731563581589L;

  /**
   * 具体类型，在对象创建时会初始化为：enum。
   */
  private String type;

  /**
   * 每一个枚举项。
   */
  private List<String> values;

  /**
   * Instantiates a new Enum type.
   */
  public EnumType() {
    this.type = FunctionDataTypeUtils.ENUM_TYPE;
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
   * Gets values.
   *
   * @return the values
   */
  public List<String> getValues() {
    return values;
  }

  /**
   * Sets values.
   *
   * @param values the values
   */
  public void setValues(List<String> values) {
    this.values = values;
  }
}
