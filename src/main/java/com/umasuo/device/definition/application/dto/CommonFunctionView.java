package com.umasuo.device.definition.application.dto;

import lombok.Data;

/**
 * Created by Davis on 17/6/28.
 */
@Data
public class CommonFunctionView {

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
   * the command send to device.
   */
  private String command;

}
