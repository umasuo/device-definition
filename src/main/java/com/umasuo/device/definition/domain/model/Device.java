package com.umasuo.device.definition.domain.model;

import com.umasuo.device.definition.infrastructure.enums.DeviceStatus;
import com.umasuo.device.definition.infrastructure.enums.DeviceType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by umasuo on 17/3/7.
 */
@Entity
@Table(name = "device_definition")
@Setter
@Getter
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
  protected ZonedDateTime createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  protected ZonedDateTime lastModifiedAt;

  /**
   * version used for update date check.
   */
  private Integer version;

  /**
   * which developer this kind of device belong to.
   */
  @Column(unique = true)
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
   * device type, identify by how the communicate with other services(app, cloud)
   */
  private DeviceType type;
}
