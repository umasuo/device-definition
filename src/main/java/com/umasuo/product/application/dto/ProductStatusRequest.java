package com.umasuo.product.application.dto;

import com.umasuo.product.infrastructure.enums.RequestType;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用于修改Product发布状态的请求.
 */
@Data
public class ProductStatusRequest {

  /**
   * 请求类型。
   */
  @NotNull
  private RequestType type;

  /**
   * Product version.
   */
  @NotNull
  private Integer version;
}
