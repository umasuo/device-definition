package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 移除产品的数据的action。
 */
@Data
public class RemoveDataDefinition implements UpdateAction {

  /**
   * The dataDefinitionId.
   */
  @NotNull
  private String dataDefinitionId;

  /**
   * Get action name: addDataDefinition.
   *
   * @return addDataDefinition
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.REMOVE_DATA_DEFINITION;
  }
}
