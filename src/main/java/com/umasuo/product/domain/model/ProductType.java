package com.umasuo.product.domain.model;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

import javax.persistence.CascadeType;
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
 * 用于系统预先定义好的一些设备类型，不同设备类型拥有不同的功能，以及数据定义.
 */
@Entity
@Table(name = "product_type")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ProductType {

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
   * 名称。
   */
  private String name;

  /**
   * 产品类别的介绍。
   */
  private String description;

  /**
   * 产品类别的分组，如大家电，厨电。
   */
  private String groupName;

  /**
   * 产品类别的图标。
   */
  private String icon;

  /**
   * 该类设备预先定义好的设备功能.
   */
  @OneToMany(cascade = CascadeType.ALL)
  @OrderBy("createdAt")
  private List<CommonFunction> functions;

  /**
   * 该累设备预先定义好的数据功能.
   */
  @ElementCollection
  private List<String> dataIds;
}
