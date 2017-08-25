package com.umasuo.product.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.product.application.dto.CommonFunctionView;
import com.umasuo.product.application.dto.action.AddProductTypeFunction;
import com.umasuo.product.domain.model.CommonFunction;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.infrastructure.enums.Category;
import com.umasuo.product.infrastructure.update.UpdateAction;

import org.springframework.util.CollectionUtils;

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
   * @return CommonFunctionView
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
   * 拷贝一份新的CommonFunction列表。
   *
   * @param functions 原始的CommonFunction列表
   * @return 新拷贝的CommonFunction列表
   */
  public static List<ProductFunction> copy(List<CommonFunction> functions) {
    List<ProductFunction> productFunctions = Lists.newArrayList();

    functions.stream().forEach(function -> productFunctions.add(copy(function)));

    return productFunctions;
  }

  /**
   * 拷贝一份新的CommonFunction实体。
   *
   * @param function CommonFunction实体
   * @return 新拷贝的CommonFunction实体
   */
  public static ProductFunction copy(CommonFunction function) {
    ProductFunction productFunction = new ProductFunction();

    productFunction.setFunctionId(function.getFunctionId());
    productFunction.setName(function.getName());
    productFunction.setDescription(function.getDescription());
    productFunction.setTransferType(function.getTransferType());
    productFunction.setDataType(function.getDataType());
    productFunction.setCategory(Category.PLATFORM);

    return productFunction;
  }

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
