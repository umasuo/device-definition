package com.umasuo.product.application.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.umasuo.product.infrastructure.enums.Category;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Transient;

/**
 * 产品的具体数据定义。
 */
@Data
public class ProductDataView implements Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = 1291157843289547677L;

  /**
   * Auto generated uuid.
   */
  private String id;

  /**
   * Version used for update date check.
   */
  private Integer version;

  /**
   * Data id defined by the developer.
   */
  private String dataId;

  /**
   * The data structure.
   */
  private String schema;

  /**
   * The dataSchema.
   */
  @Transient
  private transient JsonNode dataSchema;

  /**
   * Name build this definition.
   */
  private String name;

  /**
   * Describe the usage build this definition.
   */
  private String description;

  /**
   * The Openable.
   * True means other developers can find this data, false means not.
   */
  private Boolean openable;

  /**
   * 产品数据的类别。
   */
  private Category category;
}
