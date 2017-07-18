package com.umasuo.product.application.dto;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * DataDefinition copy request.
 * platformDataDefinitionIds and developerDataDefinitionIds can not be null both.
 */
@Data
public class CopyRequest implements Serializable{

  private static final long serialVersionUID = -4281998744354349294L;
  /**
   * product id.
   */
  private String productId;

  /**
   * PlatformDataDefinition id list.
   */
  private List<String> platformDataDefinitionIds;

  /**
   * DeveloperDataDefinition id list.
   */
  private List<String> developerDataDefinitionIds;

  /**
   * CopyRequest build 方法。
   *
   * @param productId product id
   * @param platformDataDefinitionIds PlatformDataDefinition id list
   * @param developerDataDefinitionIds DeveloperDataDefinition id list
   * @return CopyRequest
   */
  public static CopyRequest build(String productId, List<String> platformDataDefinitionIds,
      List<String> developerDataDefinitionIds) {
    CopyRequest request = new CopyRequest();

    Assert
        .isTrue(StringUtils.isNotBlank(productId), "productId can not be blank");

    request.setProductId(productId);

    boolean copyFromPlatform =
        platformDataDefinitionIds != null && !platformDataDefinitionIds.isEmpty();

    boolean copyFromDeveloper =
        developerDataDefinitionIds != null && !developerDataDefinitionIds.isEmpty();

    Assert.isTrue(copyFromPlatform || copyFromDeveloper,
        "Can not build CopyRequest with null request");

    if (copyFromPlatform) {
      request.setPlatformDataDefinitionIds(platformDataDefinitionIds);
    }

    if (copyFromDeveloper) {
      request.setDeveloperDataDefinitionIds(developerDataDefinitionIds);
    }

    return request;
  }
}
