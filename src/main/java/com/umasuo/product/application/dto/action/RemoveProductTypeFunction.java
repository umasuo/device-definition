package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 移除产品类别的功能的action。
 */
@Data
public class RemoveProductTypeFunction implements UpdateAction {

  /**
   * The functionIds.
   */
  @NotNull
  @Size(min = 1)
  private List<String> functionIds;

  /**
   * Get action name: removeProductTypeFunction.
   *
   * @return removeProductTypeFunction
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.REMOVE_PRODUCT_TYPE_FUNCTION;
  }
}
