package com.umasuo.device.definition.application.dto.action;

import com.umasuo.device.definition.infrastructure.enums.NetType;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Created by umasuo on 17/6/1.
 */
@Data
public class UpdateProduct implements UpdateAction, Serializable {

  private static final long serialVersionUID = -8600039731763078261L;

  /**
   * name of the device.
   */
  @NotNull
  private String name;

  /**
   * device icon.
   */
  @NotNull
  private String icon;

  /**
   * The openable for device.
   */
  @NotNull
  private Boolean openable;

  /**
   * device NetType, identify by how the communicate with other services(app, cloud)
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
   * wifi模组型号。
   */
  private String wifiModule;

  private String description;

  @Override
  public String getActionName() {
    return UpdateActionUtils.UPDATE_PRODUCT;
  }
}
