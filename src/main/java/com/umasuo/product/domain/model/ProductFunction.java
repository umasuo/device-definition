package com.umasuo.product.domain.model;

import com.umasuo.database.dialect.JSONBUserType;
import com.umasuo.product.application.dto.FunctionDataType;
import com.umasuo.product.infrastructure.enums.Category;
import com.umasuo.product.infrastructure.enums.TransferType;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Product function means the function that the product can support.
 * most of the function is sending command.
 * 调用功能的方式：productId + functionId
 * Created by umasuo on 17/6/28.
 */
@Entity
@Table(name = "product_function")
@Data
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "dataType", typeClass = JSONBUserType.class, parameters = {
    @Parameter(name = JSONBUserType.CLASS,
        value = "FunctionDataType")})
public class ProductFunction implements Serializable {

  private static final long serialVersionUID = -1741012173485432837L;
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
   * 用户设置的功能ID, 针对每类设备，其ID唯一.
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
   * the command that need to send.
   */
  private String command;


  @Type(type = "dataType")
  private FunctionDataType dataType;

  private TransferType transferType;

  private Category category;
}