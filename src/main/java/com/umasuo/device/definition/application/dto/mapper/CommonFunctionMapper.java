package com.umasuo.device.definition.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.device.definition.application.dto.CommonFunctionView;
import com.umasuo.device.definition.domain.model.CommonFunction;
import com.umasuo.device.definition.domain.model.DeviceFunction;

import java.util.List;

/**
 * Created by Davis on 17/6/28.
 */
public final class CommonFunctionMapper {

  /**
   * Instantiates a new Function mapper.
   */
  private CommonFunctionMapper() {
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

  public static List<DeviceFunction> copy(List<CommonFunction> functions) {
    List<DeviceFunction> deviceFunctions = Lists.newArrayList();

    functions.stream().forEach(function -> deviceFunctions.add(copy(function)));

    return deviceFunctions;
  }

  public static DeviceFunction copy(CommonFunction function) {
    DeviceFunction deviceFunction = new DeviceFunction();

    deviceFunction.setFunctionId(function.getFunctionId());
    deviceFunction.setName(function.getName());
    deviceFunction.setDescription(function.getDescription());
    deviceFunction.setCommand(function.getCommand());

    return deviceFunction;
  }
}
