package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 移除产品的功能的action。
 */
@Data
public class RemoveFunction implements UpdateAction {

  /**
   * The functionIds.
   */
  @NotNull
  @Size(min = 1)
  private List<String> functionIds;

  /**
   * Get action name: removeFunction.
   *
   * @return removeFunction
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.REMOVE_FUNCTION;
  }
}
