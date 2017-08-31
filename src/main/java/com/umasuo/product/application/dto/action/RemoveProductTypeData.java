package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 移除产品类别的数据的action。
 */
@Data
public class RemoveProductTypeData implements UpdateAction {

  /**
   * The dataDefinitionId.
   */
  @NotNull
  private String dataDefinitionId;

  /**
   * Get action name: removeProductTypeData.
   *
   * @return removeProductTypeData
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.REMOVE_PRODUCT_TYPE_DATA;
  }
}
