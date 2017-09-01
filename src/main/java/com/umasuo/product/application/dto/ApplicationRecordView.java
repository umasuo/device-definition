package com.umasuo.product.application.dto;

import com.umasuo.product.infrastructure.enums.ApplicationStatus;
import com.umasuo.product.infrastructure.enums.RecordStatus;

import lombok.Data;

/**
 * Application record view class.
 */
@Data
public class ApplicationRecordView {
  /**
   * The id.
   * Created by database when insert.
   */
  private String id;

  /**
   * The Created at.
   */
  private Long createdAt;

  /**
   * The Last modified at.
   */
  private Long lastModifiedAt;

  /**
   * Version used for update date check.
   */
  private Integer version;

  /**
   * Admin对开发者申请记录的处理结果.
   */
  private RecordStatus recordStatus;

  /**
   * Admin对开发者申请的处理结果。
   */
  private ApplicationStatus applicationStatus;


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
