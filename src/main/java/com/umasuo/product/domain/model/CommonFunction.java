package com.umasuo.product.domain.model;

import com.umasuo.database.dialect.JSONBUserType;
import com.umasuo.product.application.dto.FunctionDataType;
import com.umasuo.product.infrastructure.enums.TransferType;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Created by umasuo on 17/6/28.
 * 预先定义好的设备功能.
 */
@Entity
@Table(name = "common_function")
@Data
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "dataType", typeClass = JSONBUserType.class, parameters = {
    @Parameter(name = JSONBUserType.CLASS,
        value = "com.umasuo.product.application.dto.FunctionDataType")})
public class CommonFunction {

  /**
   * id.
   */
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
   * 短功能ID, 每种设备对应的功能ID唯一.
   */
  private String functionId;

  /**
   * 功能名字，用于展示，同一个产品唯一。
   */
  private String name;

  /**
   * 功能具体介绍。
   */
  private String description;

  /**
   * the command send to device.
   */
  private String command;

  @Type(type = "dataType")
  private FunctionDataType dataType;

  private TransferType transferType;
}
