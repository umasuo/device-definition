package com.umasuo.device.definition.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.umasuo.device.definition.infrastructure.enums.NetType;
import com.umasuo.device.definition.infrastructure.enums.ProductStatus;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by umasuo on 17/6/1.
 */
@Data
@JsonInclude(Include.ALWAYS)
public class ProductView implements Serializable {

  private static final long serialVersionUID = -8662294173374891858L;

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
   * version used for update date check.
   */
  private Integer version;

  /**
   * which developer this kind of device belong to.
   */
  private String developerId;

  /**
   * device status: published, unpublished.
   */
  private ProductStatus status;

  /**
   * name of the device.
   */
  private String name;

  private String productTypeId;

  /**
   * device icon.
   */
  private String icon;

  /**
   * 数据定义，需要提前定义好不同的数据类型.
   */
  private List<ProductDataView> dataDefinitions;

  /**
   * 功能定义。
   */
  private List<DeviceFunctionView> functions;

  /**
   * device communicationType, identify by how the communicate with other services(app, cloud)
   */
  private NetType type;

  /**
   * Open status about this device.
   * True means this device can be find by other developers and false means not.
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
   * wifi模组型号。
   */
  private String wifiModule;

  private String description;
}
