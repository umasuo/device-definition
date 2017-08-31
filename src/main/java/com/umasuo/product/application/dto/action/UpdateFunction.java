package com.umasuo.product.application.dto.action;

import com.umasuo.product.application.dto.FunctionDataType;
import com.umasuo.product.infrastructure.enums.TransferType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 更新产品的功能的action.
 */
@Data
public class UpdateFunction implements UpdateAction {

  /**
   * The id.
   */
  @NotNull
  private String id;

  /**
   * The functionId.
   */
  @NotNull
  private String functionId;

  /**
   * The name.
   */
  @NotNull
  private String name;

  /**
   * The description.
   */
  private String description;

  /**
   * The transferType.
   */
  @NotNull
  private TransferType transferType;

  /**
   * The dataType.
   */
  @NotNull
  private FunctionDataType dataType;

  /**
   * Get action name: updateFunction.
   *
   * @return updateFunction
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.UPDATE_FUNCTION;
  }
}
