package com.umasuo.device.definition.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.device.definition.application.dto.DeviceFunctionView;
import com.umasuo.device.definition.application.dto.action.AddFunction;
import com.umasuo.device.definition.application.dto.action.UpdateFunction;
import com.umasuo.device.definition.domain.model.DeviceFunction;

import java.util.List;

/**
 * Created by Davis on 17/6/28.
 */
public final class DeviceFunctionMapper {

  /**
   * Instantiates a new Function mapper.
   */
  private DeviceFunctionMapper() {
  }

  /**
   * To model list.
   *
   * @param entities the entities
   * @return the list
   */
  public static List<DeviceFunctionView> toModel(List<DeviceFunction> entities) {
    List<DeviceFunctionView> models = Lists.newArrayList();

    if (entities != null) {
      entities.stream().forEach(
          entity -> models.add(toModel(entity))
      );
    }

    return models;
  }

  public static DeviceFunctionView toModel(DeviceFunction entity) {
    DeviceFunctionView model = new DeviceFunctionView();

    model.setId(entity.getId());
    model.setFunctionId(entity.getFunctionId());
    model.setName(entity.getName());
    model.setDescription(entity.getDescription());
    model.setCommand(entity.getCommand());
    model.setDataType(entity.getDataType());
    model.setTransferType(entity.getTransferType());

    return model;
  }

  public static DeviceFunction build(AddFunction action) {
    DeviceFunction function = new DeviceFunction();

    function.setFunctionId(action.getFunctionId());
    function.setName(action.getName());
    function.setDescription(action.getDescription());
    function.setCommand(action.getCommand());
    function.setTransferType(action.getTransferType());
    function.setDataType(action.getDataType());

    return function;
  }

  public static void merge(DeviceFunction function, UpdateFunction action) {
    function.setCommand(action.getCommand());
    function.setDescription(action.getDescription());
    function.setTransferType(action.getTransferType());
    function.setName(action.getName());
    function.setFunctionId(action.getFunctionId());
    function.setDataType(action.getDataType());
  }
}
