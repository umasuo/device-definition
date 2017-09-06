package com.umasuo.product.application.dto.mapper;

import com.umasuo.product.application.dto.ProductDraft;
import com.umasuo.product.application.dto.ProductView;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.enums.ProductStatus;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umasuo on 17/6/1.
 */
public final class ProductMapper {

  private ProductMapper() {
  }

  /**
   * Convert from view to domain model
   *
   * @param draft the draft
   * @param developerId the developer id
   * @param icon the icon
   * @return the product
   */
  public static Product toModel(ProductDraft draft, String developerId, String icon) {
    Product product = new Product();

    product.setDeveloperId(developerId);
    if (StringUtils.isNotBlank(draft.getIcon())) {
      product.setIcon(draft.getIcon());
    } else {
      product.setIcon(icon);
    }
    product.setName(draft.getName());
    product.setProductType(draft.getProductTypeId());
    product.setCommunicationType(draft.getType());
    if (draft.getOpenable() != null) {
      product.setOpenable(draft.getOpenable());
    }

    product.setStatus(ProductStatus.DEVELOPING);

    product.setTestUnion(UnionMapper.build());

    return product;
  }

  /**
   * convert domain model to view.
   *
   * @param product the product
   * @return the product view
   */
  public static ProductView toView(Product product) {
    ProductView view = new ProductView();

    view.setId(product.getId());
    view.setProductTypeId(product.getProductType());
    view.setCreatedAt(product.getCreatedAt());
    view.setLastModifiedAt(product.getLastModifiedAt());
    view.setVersion(product.getVersion());
    view.setDeveloperId(product.getDeveloperId());
    view.setIcon(product.getIcon());
    view.setStatus(product.getStatus());
    view.setName(product.getName());
    view.setType(product.getCommunicationType());
    view.setOpenable(product.getOpenable());

    view.setModel(product.getModel());
    view.setFirmwareVersion(product.getFirmwareVersion());
    view.setWifiModule(product.getWifiModule());
    view.setDescription(product.getDescription());

    view.setFunctions(ProductFunctionMapper.toModel(product.getProductFunctions()));

    view.setTestUnion(UnionMapper.toView(product.getTestUnion()));

    return view;
  }

  /**
   * convert list build model to list build views.
   *
   * @param products the products
   * @return the list
   */
  public static List<ProductView> toView(List<Product> products) {
    List<ProductView> views = new ArrayList<>();
    if (products != null) {
      products.stream().forEach(
          product -> views.add(toView(product))
      );
    }
    return views;
  }
}
