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
   * 把CommonFunction列表转换为CommonFunctionView列表。
   *
   * @param entities CommonFunction list
   * @return CommonFunctionView list
   */
  public static List<CommonFunctionView> toModel(List<CommonFunction> entities) {
    List<CommonFunctionView> models = Lists.newArrayList();

    entities.stream().forEach(
        entity -> models.add(toModel(entity))
    );

    return models;
  }

  /**
   * 把CommonFunction转换为CommonFunctionView。
   *
   * @param entity CommonFunction
   * @return CommonFunctionView
   */
  public static CommonFunctionView toModel(CommonFunction entity) {
    CommonFunctionView model = new CommonFunctionView();

    model.setFunctionId(entity.getFunctionId());
    model.setName(entity.getName());
    model.setDescription(entity.getDescription());
    model.setCommand(entity.getCommand());
    model.setTransferType(entity.getTransferType());
    model.setDataType(entity.getDataType());

    return model;
  }

  /**
   * 拷贝一份新的CommonFunction列表。
   *
   * @param functions 原始的CommonFunction列表
   * @return 新拷贝的CommonFunction列表
   */
  public static List<DeviceFunction> copy(List<CommonFunction> functions) {
    List<DeviceFunction> deviceFunctions = Lists.newArrayList();

    functions.stream().forEach(function -> deviceFunctions.add(copy(function)));

    return deviceFunctions;
  }

  /**
   * 拷贝一份新的CommonFunction实体。
   *
   * @param function CommonFunction实体
   * @return 新拷贝的CommonFunction实体
   */
  public static DeviceFunction copy(CommonFunction function) {
    DeviceFunction deviceFunction = new DeviceFunction();

    deviceFunction.setFunctionId(function.getFunctionId());
    deviceFunction.setName(function.getName());
    deviceFunction.setDescription(function.getDescription());
    deviceFunction.setCommand(function.getCommand());

    return deviceFunction;
  }
}
