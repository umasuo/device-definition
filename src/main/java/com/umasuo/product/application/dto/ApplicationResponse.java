package com.umasuo.product.application.dto;

import com.umasuo.product.infrastructure.enums.RequestStatus;
import com.umasuo.product.infrastructure.enums.RecordStatus;

import lombok.Data;

/**
 * Admin 处理开发者申请的回复。
 */
@Data
public class ApplicationResponse {

  private RecordStatus recordStatus;

  private RequestStatus applicationStatus;

  /**
   * The version.
   */
  private Integer version;
}
