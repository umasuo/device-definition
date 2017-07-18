package com.umasuo.product.infrastructure.enums;

/**
 * Created by umasuo on 17/3/7.
 */
public enum ProductStatus {
  /**
   * 开发中，新建产品处于该状态，可以修改产品的任何属性。
   */
  DEVELOPING,

  /**
   * 已发布，产品完成开发调试，由开发者自主选择是否发布该产品，一旦发布，则不能修改产品的任何属性。
   */
  PUBLISHED,

  /**
   * 撤销发布，开发者下架某产品。
   */
  REVOKED
}
