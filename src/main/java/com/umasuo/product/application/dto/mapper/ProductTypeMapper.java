package com.umasuo.product.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.product.application.dto.CommonDataView;
import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.domain.model.ProductType;

import java.util.List;
import java.util.Map;

/**
 * 该类用于转换ProductType与ProductTypeView。
 *
 * Created by Davis on 17/6/28.
 */
public final class ProductTypeMapper {

  /**
   * Instantiates a new Product type mapper.
   */
  private ProductTypeMapper() {
  }

  /**
   * 把ProductType列表转换为ProductTypeView列表，并且加上对应的CommonDataView。
   *
   * @param entities ProductType list
   * @param commonDataViews CommonDataView list
   * @return list of ProductTypeView
   */
  public static List<ProductTypeView> toModel(List<ProductType> entities,
      Map<String, List<CommonDataView>> commonDataViews) {
    List<ProductTypeView> models = Lists.newArrayList();

    entities.stream().forEach(
        entity -> models.add(toModel(entity, commonDataViews))
    );

    return models;
  }

  /**
   * 把ProductType转换为ProductTypeView，并且加上对应的CommonDataView。
   *
   * @param entity ProductType
   * @param commonDataViews CommonDataView list
   * @return ProductTypeView
   */
  public static ProductTypeView toModel(ProductType entity,
      Map<String, List<CommonDataView>> commonDataViews) {
    ProductTypeView model = new ProductTypeView();

    model.setName(entity.getName());
    model.setGroupName(entity.getGroupName());
    model.setId(entity.getId());
    model.setFunctions(CommonFunctionMapper.toModel(entity.getFunctions()));

    List<CommonDataView> dataViews = commonDataViews.get(entity.getId());
    model.setData(dataViews);

    return model;
  }
}
