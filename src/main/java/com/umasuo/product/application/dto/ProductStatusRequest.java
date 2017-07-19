package com.umasuo.product.application.dto;

import com.umasuo.product.infrastructure.enums.RequestType;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/7/4.
 */
@Data
public class ProductStatusRequest {

  @NotNull
  private RequestType type;

  @NotNull
  private Integer version;
}
