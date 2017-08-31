package com.umasuo.product.application.dto;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Transient;

/**
 * Created by Davis on 17/6/28.
 */
@Data
public class CommonDataView implements Serializable{

  private static final long serialVersionUID = -8861282658167694700L;

  private String id;

  private String dataId;

  private String name;

  /**
   * version used for update date check.
   */
  private Integer version;

  /**
   * the data structure.
   */
  private String schema;

  @Transient
  private transient JsonNode dataSchema;

  private String description;
}
