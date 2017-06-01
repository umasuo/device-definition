package com.umasuo.device.definition.application.dto.action;

import com.umasuo.device.definition.infrastructure.enums.DeviceType;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Created by umasuo on 17/6/1.
 */
@Data
public class UpdateDevice implements UpdateAction, Serializable {

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
   * device type, identify by how the communicate with other services(app, cloud)
   */
  @NotNull
  private DeviceType type;

  @Override
  public String getActionName() {
    return UpdateActionUtils.UPDATE_DATA_DEFINITION;
  }
}
