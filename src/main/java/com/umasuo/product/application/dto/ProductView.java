package com.umasuo.product.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.umasuo.product.infrastructure.enums.NetType;
import com.umasuo.product.infrastructure.enums.ProductStatus;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 产品。
 */
@Data
@JsonInclude(Include.ALWAYS)
public class ProductView implements Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -8662294173374891858L;

  /**
   * The id.
   */
  private String id;

  /**
   * The Created at.
   */
  protected Long createdAt;

  /**
   * The Last modified at.
   */
  protected Long lastModifiedAt;

  /**
   * Version used for update date check.
   */
  private Integer version;

  /**
   * Which developer this kind build product belong to.
   */
  private String developerId;

  /**
   * Product status: published, unpublished.
   */
  private ProductStatus status;

  /**
   * name build the Product.
   */
  private String name;

  /**
   * The productType id.
   */
  private String productTypeId;

  /**
   * Product icon.
   */
  private String icon;

  /**
   * 数据定义，需要提前定义好不同的数据类型.
   */
  private List<ProductDataView> dataDefinitions;

  /**
   * 功能定义。
   */
  private List<ProductFunctionView> functions;

  /**
   * Product net type, identify by how the communicate with other services(app, cloud)
   */
  private NetType type;

  /**
   * Open status about this product.
   * True means this product can be find by other developers and false means not.
   */
  private Boolean openable = false;

  /**
   * 产品的固件版本信息。
   */
  private String firmwareVersion;

  /**
   * 开发者自定义的设备型号。
   */
  private String model;

  /**
   * Wifi模组型号。
   */
  private String wifiModule;

  /**
   * The description.
   */
  private String description;

  /**
   * 用于开发测试的union id。
   */
  private TestUnionView testUnion;
}
