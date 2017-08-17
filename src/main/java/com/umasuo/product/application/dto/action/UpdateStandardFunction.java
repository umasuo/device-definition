package com.umasuo.product.application.dto.action;

import com.umasuo.product.application.dto.FunctionDataType;
import com.umasuo.product.infrastructure.enums.TransferType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/8/17.
 */
@Data
public class UpdateStandardFunction implements UpdateAction {

  @NotNull
  private String id;

  @NotNull
  private String name;

  private String description;

  @NotNull
  private TransferType transferType;

  @NotNull
  private FunctionDataType dataType;

  @Override
  public String getActionName() {
    return UpdateActionUtils.UPDATE_STANDARD_FUNCTION;
  }
}
