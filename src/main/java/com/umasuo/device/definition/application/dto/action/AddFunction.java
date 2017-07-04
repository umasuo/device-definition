package com.umasuo.device.definition.application.dto.action;

import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/7/4.
 */
@Data
public class AddFunction implements UpdateAction {

  @NotNull
  private String functionId;

  @NotNull
  private String name;

  private String description;

  @NotNull
  private String command;


  @Override
  public String getActionName() {
    return UpdateActionUtils.ADD_FUNCTION;
  }
}