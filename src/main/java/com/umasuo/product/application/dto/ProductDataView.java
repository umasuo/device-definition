package com.umasuo.product.application.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.umasuo.product.infrastructure.enums.Category;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Transient;

/**
 * Created by Davis on 17/7/12.
 */
@Data
public class ProductDataView implements Serializable {

  private static final long serialVersionUID = 1291157843289547677L;

  /**
   * auto generated uuid.
   */
  private String id;

  /**
   * version used for update date check.
   */
  private Integer version;

  /**
   * data id defined by the developer.
   */
  private String dataId;

  /**
   * the data structure.
   */
  private String schema;

  @Transient
  private transient JsonNode dataSchema;

  /**
   * name of this definition.
   */
  private String name;

  /**
   * describe the usage of this definition.
   */
  private String description;

  /**
   * The Openable.
   * True means other developers can find this data, false means not.
   */
  private Boolean openable;

  private Category category;
}
