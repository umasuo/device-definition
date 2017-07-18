package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.enums.ProductStatus;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/7/4.
 */
@Data
public class UpdateStatus {

  @NotNull
  private ProductStatus status;

  @NotNull
  private Integer version;
}
