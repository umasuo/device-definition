package com.umasuo.product.application.dto;

import com.umasuo.product.infrastructure.enums.TransferType;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Davis on 17/6/28.
 */
@Data
public class CommonFunctionView implements Serializable{

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

  private TransferType transferType;

  private FunctionDataType dataType;
}
