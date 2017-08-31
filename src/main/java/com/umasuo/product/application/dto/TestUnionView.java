package com.umasuo.product.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 开发测试使用的UnionId。
 */
@Data
public class TestUnionView implements Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -2570826887391517208L;

  /**
   * The unionId.
   */
  private String unionId;

  /**
   * The secretKey.
   */
  private String secretKey;
}
