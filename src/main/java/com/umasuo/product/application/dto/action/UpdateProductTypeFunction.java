package com.umasuo.product.application.dto.action;

import com.umasuo.product.application.dto.FunctionDataType;
import com.umasuo.product.infrastructure.enums.TransferType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by rai on 2017/8/28.
 */
@Data
public class UpdateProductTypeFunction implements UpdateAction {

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
    return UpdateActionUtils.UPDATE_PRODUCT_TYPE_FUNCTION;
  }
}
