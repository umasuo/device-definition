package com.umasuo.device.definition.application.dto;

import com.umasuo.device.definition.infrastructure.enums.Category;
import com.umasuo.device.definition.infrastructure.enums.TransferType;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Davis on 17/6/29.
 */
@Data
public class DeviceFunctionView implements Serializable{

  private static final long serialVersionUID = 6963500596139117164L;
  
  private String id;

  private String functionId;

  private String name;

  private String description;

  private String command;

  private TransferType transferType;

  private FunctionDataType dataType;

  private Category category;
}
