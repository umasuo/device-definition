package com.umasuo.product.domain.model;

import com.umasuo.product.infrastructure.enums.RequestStatus;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
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
 * 开发者申请变更产品状态的记录.
 */
@Entity
@Table(name = "status_request")
@Data
@EntityListeners(AuditingEntityListener.class)
public class StatusRequest {

  /**
   * The id.
   * Created by database when insert.
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
   * Version used for update date check.
   */
  @Version
  private Integer version;

  /**
   * 申请记录的处理状态。
   */
  private RequestStatus status;

  /**
   * The developerId.
   */
  private String developerId;

  /**
   * The productId.
   */
  private String productId;

  /**
   * 备注。
   */
  private String remark;
}
