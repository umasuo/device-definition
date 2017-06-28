package com.umasuo.device.definition.domain.model;

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
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Created by umasuo on 17/6/28.
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

  private String name;

  private String description;

  /**
   * 该类设备预先定义好的设备功能.
   */
  @OneToMany
  private List<CommonFunction> functions;

  /**
   * 该累设备预先定义好的数据功能.
   */
  @ElementCollection
  private List<String> dataIds;


}
