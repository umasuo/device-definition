package com.umasuo.device.definition.domain.model;

import com.umasuo.device.definition.infrastructure.enums.CommunicationType;
import com.umasuo.device.definition.infrastructure.enums.DeviceStatus;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Created by umasuo on 17/3/7.
 */
@Entity
@Table(name = "device_definition")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Device {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  private String id;

  /**
   * The Created at.
   */
  @CreatedDate
  @Column(name = "created_at")
  protected Long createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  protected Long lastModifiedAt;

  /**
   * version used for update date check.
   */
  @Version
  private Integer version;

  /**
   * which developer this kind of device belong to.
   */
  private String developerId;

  /**
   * device status: developing, published, revoked.
   */
  private DeviceStatus status;

  /**
   * name of the device.
   */
  @Column(nullable = false)
  private String name;

  /**
   * device icon.
   */
  private String icon;

  /**
   * 产品类型，新建产品时选择的系统预设产品类型，或者是自定义的类型。
   */
  private String productType;

  /**
   * 开发者自定义的设备型号。
   */
  private String model;

  /**
   * 产品的详细介绍。
   * 长度限制为65535。
   */
  @Column(length = 65535)
  private String description;

  /**
   * 产品的固件版本信息。
   */
  private String firmwareVersion;

  /**
   * 数据定义ID，需要提前定义好不同的数据类型.
   */
  @ElementCollection
  private List<String> dataDefineIds;

  /**
   * 产品的功能列表。
   */
  @OneToMany
  @OrderBy("functionId")
  private List<DeviceFunction> deviceFunctions;

  /**
   * device communicationType, identify by how the communicate with other services(app, cloud)
   */
  private CommunicationType communicationType;

  /**
   * wifi模组型号。
   */
  private String wifiModule;

  /**
   * Open status about this device.
   * True means this device can be find by other developers and false means not.
   */
  private Boolean openable = false;
}
