package com.umasuo.product.application.dto;

import com.umasuo.product.infrastructure.util.FunctionDataTypeUtils;

import lombok.ToString;

import java.io.Serializable;

/**
 * 数值类型，用于Function。
 */
@ToString
public class ValueType implements FunctionDataType, Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -5780098908699303181L;

  /**
   * 具体类型，在对象创建时会初始化为：value。
   */
  private String type;

  /**
   * 开始值。
   */
  private Long startValue;

  /**
   * 结束值。
   */
  private Long endValue;

  /**
   * 间隔。
   */
  private Long interval;

  /**
   * 倍数
   */
  private Long multiple;

  /**
   * 单位。
   */
  private String unit;

  /**
   * Instantiates a new Value type.
   */
  public ValueType() {
    this.type = FunctionDataTypeUtils.VALUE_TYPE;
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
   * Gets start value.
   *
   * @return the start value
   */
  public Long getStartValue() {
    return startValue;
  }

  /**
   * Sets start value.
   *
   * @param startValue the start value
   */
  public void setStartValue(Long startValue) {
    this.startValue = startValue;
  }

  /**
   * Gets end value.
   *
   * @return the end value
   */
  public Long getEndValue() {
    return endValue;
  }

  /**
   * Sets end value.
   *
   * @param endValue the end value
   */
  public void setEndValue(Long endValue) {
    this.endValue = endValue;
  }

  /**
   * Gets interval.
   *
   * @return the interval
   */
  public Long getInterval() {
    return interval;
  }

  /**
   * Sets interval.
   *
   * @param interval the interval
   */
  public void setInterval(Long interval) {
    this.interval = interval;
  }

  /**
   * Gets multiple.
   *
   * @return the multiple
   */
  public Long getMultiple() {
    return multiple;
  }

  /**
   * Sets multiple.
   *
   * @param multiple the multiple
   */
  public void setMultiple(Long multiple) {
    this.multiple = multiple;
  }

  /**
   * Gets unit.
   *
   * @return the unit
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Sets unit.
   *
   * @param unit the unit
   */
  public void setUnit(String unit) {
    this.unit = unit;
  }
}
