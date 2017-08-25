package com.umasuo.product.application.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/8/25.
 */
@Data
public class ProductTypeDraft {
  @NotNull
  private String name;

  @NotNull
  private String groupName;
}
