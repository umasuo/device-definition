package com.umasuo.device.definition.application.dto.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/7/7.
 */
@Data
public class AddDataDefinition implements UpdateAction{

  /**
   * 数据格点ID，需要对开发者唯一，例如: s001。
   */
  @NotNull
  private String dataId;

  /**
   * 数据定义的名称，例如"手环步数"
   */
  @NotNull
  private String name;

  /**
   * 产品ID.
   */
  private String productId;
  /**
   * 数据定义介绍，主要用于介绍此数据格点的用途，目的等。
   */
  private String description;

  /**
   * 数据具体的结构.
   */
  @NotNull
  private JsonNode dataSchema;

  /**
   * The Openable.
   * True means other developers can find this data, false means not.
   * Default is false;
   */
  private Boolean openable;

  @Override
  public String getActionName() {
    return UpdateActionUtils.ADD_DATA_DEFINITION;
  }
}
