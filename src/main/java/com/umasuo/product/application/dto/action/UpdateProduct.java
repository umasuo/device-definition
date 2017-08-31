package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.enums.NetType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 更新product信息的action。
 */
@Data
public class UpdateProduct implements UpdateAction, Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -8600039731763078261L;

  /**
   * Name build the product.
   */
  @NotNull
  private String name;

  /**
   * Product icon.
   */
  @NotNull
  private String icon;

  /**
   * The openable for product.
   */
  @NotNull
  private Boolean openable;

  /**
   * Product NetType, identify by how the communicate with other services(app, cloud)
   */
  @NotNull
  private NetType type;

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
   * Get action name: updateProduct.
   *
   * @return updateProduct
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.UPDATE_PRODUCT;
  }
}
