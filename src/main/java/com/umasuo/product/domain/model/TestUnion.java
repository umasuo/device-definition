package com.umasuo.product.domain.model;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * 用于开发测试的unionId和secretKey.
 */
@Data
@Embeddable
public class TestUnion {

  /**
   * The unionId.
   */
  private String unionId;

  /**
   * The secretKey.
   */
  private String secretKey;
}
