package com.umasuo.product.application.dto.mapper;

import com.umasuo.product.application.dto.StatusRequestView;
import com.umasuo.product.domain.model.StatusRequest;
import com.umasuo.product.infrastructure.enums.RequestStatus;

/**
 * Created by Davis on 17/7/19.
 */
public final class RequestMapper {

  private RequestMapper() {
  }

  public static StatusRequest build(String developerId, String productId) {
    StatusRequest statusRequest = new StatusRequest();

    statusRequest.setDeveloperId(developerId);
    statusRequest.setProductId(productId);
    statusRequest.setStatus(RequestStatus.UNVIEW);

    return statusRequest;
  }

  public static StatusRequestView toModel(StatusRequest statusRequest) {
    // TODO: 17/7/19
    return null;
  }
}
