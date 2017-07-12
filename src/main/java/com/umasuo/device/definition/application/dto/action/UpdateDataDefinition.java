package com.umasuo.device.definition.application.dto.action;

import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/7/12.
 */
@Data
public class UpdateDataDefinition implements UpdateAction{

  @NotNull
  private String dataDefinitionId;

  @NotNull
  private String dataId;

  @NotNull
  private String name;

  @NotNull
  private String schema;

  @NotNull
  private String description;

  @NotNull
  private Boolean openable;

  @Override
  public String getActionName() {
    return UpdateActionUtils.UPDATE_DATA_DEFINITION;
  }
}
