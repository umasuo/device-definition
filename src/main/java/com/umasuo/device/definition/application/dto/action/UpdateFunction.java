package com.umasuo.device.definition.application.dto.action;

import com.umasuo.device.definition.application.dto.DeviceFunctionView;
import com.umasuo.device.definition.application.dto.FunctionDataType;
import com.umasuo.device.definition.infrastructure.enums.TransferType;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/7/7.
 */
@Data
public class UpdateFunction implements UpdateAction {

  @NotNull
  private String id;

  @NotNull
  private String functionId;

  @NotNull
  private String name;

  private String description;

  @NotNull
  private String command;

  @NotNull
  private TransferType transferType;

  @NotNull
  private FunctionDataType dataType;

  @Override
  public String getActionName() {
    return UpdateActionUtils.UPDATE_FUNCTION;
  }
}
