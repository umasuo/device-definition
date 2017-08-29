package com.umasuo.product.application.dto.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/7/12.
 */
@Data
public class UpdateProductTypeData implements UpdateAction {

  @NotNull
  private String dataDefinitionId;

  @NotNull
  private String name;

  @NotNull
  private JsonNode dataSchema;

  private String description;

  @NotNull
  private Boolean openable;

  @NotNull
  private Integer version;

  @Override
  public String getActionName() {
    return UpdateActionUtils.UPDATE_PRODUCT_TYPE_DATA;
  }
}
