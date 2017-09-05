package com.umasuo.product.domain.model;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * 用于开发测试的unionId和secretKey.
 */
@Data
@Embeddable
public class TestUnion implements Serializable{

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = 843509058285040638L;

  /**
   * The unionId.
   */
  private String unionId;

  /**
   * The secretKey.
   */
  private String secretKey;
}
