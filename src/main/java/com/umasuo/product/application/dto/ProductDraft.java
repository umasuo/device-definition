package com.umasuo.product.application.dto;

import com.umasuo.product.infrastructure.enums.NetType;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用于创建产品。
 */
@Data
public class ProductDraft {

  /**
   * Name build the product.
   */
  @NotNull(message = "Name can not be null")
  private String name;

  /**
   * Description build the product.
   */
  private String description;

  /**
   * Product icon.
   */
  private String icon;

  /**
   * 产品类型的ID
   */
  @NotNull(message = "ProductType can not be null")
  private String productTypeId;

  /**
   * Product net type, identify by how the product connect to the internet.
   */
  @NotNull(message = "NetType can not be null")
  private NetType type;

  /**
   * Open status about this product.
   * True means this product can be find by other developers and false means not.
   */
  private Boolean openable;
}
