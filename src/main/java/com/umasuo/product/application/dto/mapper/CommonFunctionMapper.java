package com.umasuo.product.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.product.application.dto.CommonFunctionView;
import com.umasuo.product.application.dto.action.AddProductTypeFunction;
import com.umasuo.product.domain.model.CommonFunction;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Mapper class for CommonFunction.
 */
public final class CommonFunctionMapper {

  /**
   * Private constructor.
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

    if (!CollectionUtils.isEmpty(entities)) {
      entities.stream().forEach(
          entity -> models.add(toModel(entity))
      );
    }

    return models;
  }

  /**
   * 把CommonFunction转换为CommonFunctionView。
   *
   * @param entity CommonFunction
   * @return CommonFunctionView common function view
   */
  public static CommonFunctionView toModel(CommonFunction entity) {
    CommonFunctionView model = new CommonFunctionView();

    model.setId(entity.getId());
    model.setFunctionId(entity.getFunctionId());
    model.setName(entity.getName());
    model.setDescription(entity.getDescription());
    model.setTransferType(entity.getTransferType());
    model.setDataType(entity.getDataType());

    return model;
  }

  /**
   * Build CommonFunction.
   *
   * @param action the action
   * @return common function
   */
  public static CommonFunction build(AddProductTypeFunction action) {
    CommonFunction function = new CommonFunction();

    function.setFunctionId(action.getFunctionId());
    function.setName(action.getName());
    function.setDescription(action.getDescription());
    function.setTransferType(action.getTransferType());
    function.setDataType(action.getDataType());

    return function;
  }
}
