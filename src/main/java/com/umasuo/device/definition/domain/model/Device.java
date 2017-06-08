package com.umasuo.device.definition.domain.model;

import com.umasuo.device.definition.infrastructure.enums.DeviceStatus;
import com.umasuo.device.definition.infrastructure.enums.DeviceType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
  @Version
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
  @ElementCollection
  private List<String> dataDefineIds;

  /**
   * device type, identify by how the communicate with other services(app, cloud)
   */
  private DeviceType type;

  /**
   * Open status about this device.
   * True means this device can be find by other developers and false means not.
   */
  private Boolean openable = false;
}
