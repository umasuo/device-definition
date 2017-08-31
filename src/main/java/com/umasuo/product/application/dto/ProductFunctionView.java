package com.umasuo.product.application.dto;

import com.umasuo.product.infrastructure.enums.Category;
import com.umasuo.product.infrastructure.enums.TransferType;

import lombok.Data;

import java.io.Serializable;

/**
 * 产品功能。
 */
@Data
public class ProductFunctionView implements Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = 6963500596139117164L;

  /**
   * The id.
   */
  private String id;

  /**
   * The functionId.
   */
  private String functionId;

  /**
   * The name.
   */
  private String name;

  /**
   * The description.
   */
  private String description;

  /**
   * THe transfer type.
   */
  private TransferType transferType;

  /**
   * The dataType.
   */
  private FunctionDataType dataType;

  /**
   * The category.
   */
  private Category category;
}
