package com.umasuo.device.definition.application.dto.action;

import com.umasuo.device.definition.infrastructure.enums.ProductStatus;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/7/4.
 */
@Data
public class SetStatus implements UpdateAction {

  @NotNull
  private ProductStatus status;

  @Override
  public String getActionName() {
    return UpdateActionUtils.SET_STATUS;
  }
}
