package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/8/25.
 */
@Data
public class UpdateProductType implements UpdateAction {

  @NotNull
  private String name;

  @NotNull
  private String groupName;

  @Override
  public String getActionName() {
    return UpdateActionUtils.UPDATE_PRODUCT_TYPE;
  }
}
