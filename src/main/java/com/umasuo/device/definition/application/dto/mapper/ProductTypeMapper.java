package com.umasuo.device.definition.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.device.definition.application.dto.CommonDataView;
import com.umasuo.device.definition.application.dto.ProductTypeView;
import com.umasuo.device.definition.domain.model.ProductType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Davis on 17/6/28.
 */
public final class ProductTypeMapper {

  /**
   * Instantiates a new Product type mapper.
   */
  private ProductTypeMapper() {
  }


  public static List<ProductTypeView> toModel(List<ProductType> entities,
      List<CommonDataView> dataDefinitionViews) {
    List<ProductTypeView> models = Lists.newArrayList();

    entities.stream().forEach(
        entity -> models.add(toModel(entity, dataDefinitionViews))
    );

    return models;
  }

  public static ProductTypeView toModel(ProductType entity,
      List<CommonDataView> dataDefinitionViews) {
    ProductTypeView model = new ProductTypeView();

    model.setName(entity.getName());
    model.setGroupName(entity.getGroupName());
    model.setId(entity.getId());
    model.setFunctions(CommonFunctionMapper.toModel(entity.getFunctions()));

    List<CommonDataView> modelDataDefinitions = dataDefinitionViews.stream().filter(
        view -> entity.getDataIds().contains(view.getId())
    ).collect(Collectors.toList());

    model.setData(modelDataDefinitions);

    return model;
  }
}
