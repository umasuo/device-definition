package com.umasuo.product.application.dto;

import com.umasuo.product.infrastructure.enums.TransferType;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于ProductType的功能。
 */
@Data
public class CommonFunctionView implements Serializable{

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = 7869266662203116465L;

  /**
   * 系统唯一ID
   */
  private String id;

  /**
   * 短功能ID, 每种设备对应的功能ID唯一.
   */
  private String functionId;

  /**
   * 功能名字，用于展示，同一个产品唯一。
   */
  private String name;

  /**
   * 功能具体介绍。
   */
  private String description;

  /**
   * 传输类型，包括：UPDOWN － 上下行，UP － 上行，DOWN － 下行。
   */
  private TransferType transferType;

  /**
   * 数据类型，包括：boolean － 布尔型，value － 数值型，enum － 枚举型， string － 字符型。
   */
  private FunctionDataType dataType;
}
