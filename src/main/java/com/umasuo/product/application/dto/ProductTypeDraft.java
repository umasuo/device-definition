package com.umasuo.product.application.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用于创建产品类型。
 */
@Data
public class ProductTypeDraft {

  /**
   * The name.
   */
  @NotNull
  private String name;

  /**
   * The groupName.
   */
  @NotNull
  private String groupName;
}
