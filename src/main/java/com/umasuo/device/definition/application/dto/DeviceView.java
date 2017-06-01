package com.umasuo.device.definition.application.dto;

import com.umasuo.device.definition.infrastructure.enums.DeviceStatus;
import com.umasuo.device.definition.infrastructure.enums.DeviceType;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by umasuo on 17/6/1.
 */
@Data
public class DeviceView implements Serializable {

  private static final long serialVersionUID = -8662294173374891858L;

  private String id;

  /**
   * The Created at.
   */
  protected ZonedDateTime createdAt;

  /**
   * The Last modified at.
   */
  protected ZonedDateTime lastModifiedAt;

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
  private DeviceStatus status;

  /**
   * name of the device.
   */
  private String name;

  /**
   * device icon.
   */
  private String icon;

  /**
   * 数据定义ID，需要提前定义好不同的数据类型.
   */
  private List<String> dataDefineIds;

  /**
   * device type, identify by how the communicate with other services(app, cloud)
   */
  private DeviceType type;
}
