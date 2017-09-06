package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 更新产品类别的数据的action.
 */
@Data
public class UpdateProductTypeData implements UpdateAction {

  /**
   * The dataDefinitionId.
   */
  @NotNull
  private String dataDefinitionId;

  /**
   * The name.
   */
  @NotNull
  private String name;

  /**
   * The dataSchema.
   */
  @NotNull
  private String schema;

  /**
   * The description.
   */
  private String description;

  /**
   * The version.
   */
  private Integer version;

  /**
   * Get action name: updateProductTypeData.
   *
   * @return updateProductTypeData
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.UPDATE_PRODUCT_TYPE_DATA;
  }
}
