package com.umasuo.product.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.product.application.dto.ProductFunctionView;
import com.umasuo.product.application.dto.action.AddFunction;
import com.umasuo.product.application.dto.action.UpdateFunction;
import com.umasuo.product.application.dto.action.UpdateStandardFunction;
import com.umasuo.product.domain.model.CommonFunction;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.infrastructure.enums.Category;

import java.util.List;

/**
 * Mapper class for ProductFunction.
 */
public final class ProductFunctionMapper {

  /**
   * Private constructor.
   */
  private ProductFunctionMapper() {
  }

  /**
   * To model list.
   *
   * @param entities the entities
   * @return the list
   */
  public static List<ProductFunctionView> toModel(List<ProductFunction> entities) {
    List<ProductFunctionView> models = Lists.newArrayList();

    if (entities != null) {
      entities.stream().forEach(
          entity -> models.add(toModel(entity))
      );
    }

    return models;
  }

  /**
   * Convert ProductFunction to ProductFunctionView.
   *
   * @param entity the entity
   * @return the product function view
   */
  public static ProductFunctionView toModel(ProductFunction entity) {
    ProductFunctionView model = new ProductFunctionView();

    model.setId(entity.getId());
    model.setFunctionId(entity.getFunctionId());
    model.setName(entity.getName());
    model.setDescription(entity.getDescription());
    model.setDataType(entity.getDataType());
    model.setTransferType(entity.getTransferType());
    model.setCategory(entity.getCategory());

    return model;
  }


  /**
   * Build ProductFunction from AddFunction.
   *
   * @param action the action
   * @return the product function
   */
  public static ProductFunction build(AddFunction action) {
    ProductFunction function = new ProductFunction();

    function.setFunctionId(action.getFunctionId());
    function.setName(action.getName());
    function.setDescription(action.getDescription());
    function.setTransferType(action.getTransferType());
    function.setDataType(action.getDataType());
    function.setCategory(Category.PRODUCT);

    return function;
  }


  /**
   * Merge UpdateFunction action into ProductFunction.
   *
   * @param function the function
   * @param action the action
   */
  public static void merge(ProductFunction function, UpdateFunction action) {
    function.setDescription(action.getDescription());
    function.setTransferType(action.getTransferType());
    function.setName(action.getName());
    function.setDataType(action.getDataType());

    function.setFunctionId(action.getFunctionId());
  }


  /**
   * Merge UpdateStandardFunction action into ProductFunction.
   *
   * @param function the function
   * @param action the action
   */
  public static void merge(ProductFunction function, UpdateStandardFunction action) {
    function.setDescription(action.getDescription());
    function.setTransferType(action.getTransferType());
    function.setName(action.getName());
    function.setDataType(action.getDataType());
  }

  /**
   * 拷贝一份新的CommonFunction列表。
   *
   * @param functions 原始的CommonFunction列表
   * @return 新拷贝的CommonFunction列表 list
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
   * @return 新拷贝的CommonFunction实体 product function
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
}
