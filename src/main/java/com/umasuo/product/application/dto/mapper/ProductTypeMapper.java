package com.umasuo.product.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.product.application.dto.CommonDataView;
import com.umasuo.product.application.dto.ProductTypeDraft;
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
  public static List<ProductTypeView> toView(List<ProductType> entities,
      Map<String, List<CommonDataView>> commonDataViews) {
    List<ProductTypeView> views = Lists.newArrayList();

    entities.stream().forEach(
        entity -> views.add(toView(entity, commonDataViews))
    );

    return views;
  }

  /**
   * 把ProductType转换为ProductTypeView，并且加上对应的CommonDataView。
   *
   * @param entity ProductType
   * @param commonDataViews CommonDataView list
   * @return ProductTypeView
   */
  public static ProductTypeView toView(ProductType entity,
      Map<String, List<CommonDataView>> commonDataViews) {
    ProductTypeView view = toView(entity);

    List<CommonDataView> dataViews = commonDataViews.get(entity.getId());
    view.setData(dataViews);

    return view;
  }

  public static ProductTypeView toView(ProductType entity) {
    ProductTypeView view = new ProductTypeView();

    view.setId(entity.getId());
    view.setName(entity.getName());
    view.setGroupName(entity.getGroupName());
    view.setFunctions(CommonFunctionMapper.toModel(entity.getFunctions()));

    return view;
  }


  public static ProductType toModel(ProductTypeDraft draft) {
    ProductType productType = new ProductType();

    productType.setName(draft.getName());
    productType.setGroupName(draft.getGroupName());

    return productType;
  }
}
