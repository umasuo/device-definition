package com.umasuo.product.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.product.application.dto.ProductFunctionView;
import com.umasuo.product.application.dto.action.AddFunction;
import com.umasuo.product.application.dto.action.UpdateFunction;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.infrastructure.enums.Category;

import java.util.List;

/**
 * Created by Davis on 17/6/28.
 */
public final class ProductFunctionMapper {

  /**
   * Instantiates a new Function mapper.
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

  public static void merge(ProductFunction function, UpdateFunction action) {
    function.setDescription(action.getDescription());
    function.setTransferType(action.getTransferType());
    function.setName(action.getName());
    function.setFunctionId(action.getFunctionId());
    function.setDataType(action.getDataType());
  }
}
