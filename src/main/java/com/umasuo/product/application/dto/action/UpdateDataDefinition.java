package com.umasuo.product.application.dto.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 更新产品的数据定义的action。
 */
@Data
public class UpdateDataDefinition implements UpdateAction {

  /**
   * The dataDefinitionId.
   */
  @NotNull
  private String dataDefinitionId;

  /**
   * The dataId.
   */
  @NotNull
  private String dataId;

  /**
   * The name.
   */
  @NotNull
  private String name;

  /**
   * The dataSchema.
   */
  @NotNull
  private JsonNode dataSchema;

  /**
   * The description.
   */
  private String description;

  /**
   * The openable.
   */
  @NotNull
  private Boolean openable;

  /**
   * The version.
   */
  @NotNull
  private Integer version;

  /**
   * Get action name: updateDataDefinition.
   *
   * @return updateDataDefinition
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.UPDATE_DATA_DEFINITION;
  }
}
