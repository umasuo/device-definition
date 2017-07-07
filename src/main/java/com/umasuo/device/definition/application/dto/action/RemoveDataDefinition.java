package com.umasuo.device.definition.application.dto.action;

import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by umasuo on 17/6/1.
 */
@Data
public class RemoveDataDefinition implements UpdateAction {

  @NotNull
  private String dataDefinitionId;

  @Override
  public String getActionName() {
    return UpdateActionUtils.REMOVE_DATA_DEFINITION;
  }
}
