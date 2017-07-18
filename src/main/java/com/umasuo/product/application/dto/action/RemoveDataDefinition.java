package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

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
