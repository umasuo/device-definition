package com.umasuo.device.definition.application.dto;

import com.umasuo.device.definition.infrastructure.enums.DeviceStatus;
import com.umasuo.device.definition.infrastructure.enums.DeviceType;
import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created by umasuo on 17/6/1.
 */
@Data
public class DeviceDraft {

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
   * 数据定义ID，需要提前定义好不同的数据类型.
   */
  private List<String> dataDefineIds;

  /**
   * device type, identify by how the communicate with other services(app, cloud)
   */
  private DeviceType type;

  /**
   * Open status about this device.
   * True means this device can be find by other developers and false means not.
   */
  private Boolean openable;
}
