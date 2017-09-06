package com.umasuo.product.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Davis on 17/6/28.
 */
@Data
public class CommonDataView implements Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -8861282658167694700L;

  /**
   * The id.
   */
  private String id;

  /**
   * The dataId.
   */
  private String dataId;

  /**
   * The name.
   */
  private String name;

  /**
   * Version used for update date check.
   */
  private Integer version;

  /**
   * The data structure.
   */
  private String schema;

  /**
   * The description.
   */
  private String description;
}
