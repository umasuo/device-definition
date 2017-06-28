package com.umasuo.device.definition.application.dto.mapper;

import com.google.api.client.util.Lists;
import com.umasuo.device.definition.application.dto.CommonFunctionView;
import com.umasuo.device.definition.domain.model.CommonFunction;

import java.util.List;

/**
 * Created by Davis on 17/6/28.
 */
public final class FunctionMapper {

  /**
   * Instantiates a new Function mapper.
   */
  private FunctionMapper() {
  }

  /**
   * To model list.
   *
   * @param entities the entities
   * @return the list
   */
  public static List<CommonFunctionView> toModel(List<CommonFunction> entities) {
    List<CommonFunctionView> models = Lists.newArrayList();

    entities.stream().forEach(
        entity -> models.add(toModel(entity))
    );

    return models;
  }

  public static CommonFunctionView toModel(CommonFunction entity) {
    CommonFunctionView model = new CommonFunctionView();

    model.setFunctionId(entity.getFunctionId());
    model.setName(entity.getName());
    model.setDescription(entity.getDescription());
    model.setCommand(entity.getCommand());

    return model;
  }
}
