package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 更新产品类别信息的action.
 */
@Data
public class UpdateProductType implements UpdateAction {

  /**
   * The name.
   */
  @NotNull
  private String name;

  /**
   * The groupName.
   */
  @NotNull
  private String groupName;

  /**
   * 产品类别的图标。
   */
  private String icon;

  /**
   * Get action name: updateProductType.
   *
   * @return updateProductType
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.UPDATE_PRODUCT_TYPE;
  }
}
