package com.umasuo.device.definition.application.dto;

import lombok.Data;

/**
 * Created by Davis on 17/6/29.
 */
@Data
public class DeviceFunctionView {

  private String id;

  private String functionId;

  private String name;

  private String description;

  private String command;
}
