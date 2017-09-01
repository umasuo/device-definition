package com.umasuo.product.application.dto;

import com.umasuo.product.infrastructure.enums.ApplicationStatus;
import com.umasuo.product.infrastructure.enums.RecordStatus;
import com.umasuo.product.infrastructure.enums.RequestStatus;

import lombok.Data;

/**
 * Admin 处理开发者申请的回复。
 */
@Data
public class ApplicationResponse {

  private RecordStatus recordStatus;

  private ApplicationStatus applicationStatus;

  /**
   * The version.
   */
  private Integer version;
}
