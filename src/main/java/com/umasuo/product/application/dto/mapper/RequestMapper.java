package com.umasuo.product.application.dto.mapper;

import com.umasuo.product.application.dto.StatusRequestView;
import com.umasuo.product.domain.model.StatusRequest;
import com.umasuo.product.infrastructure.enums.RequestStatus;

/**
 * Mapper class for StatusRequest.
 */
public final class RequestMapper {

  /**
   * Private constructor.
   */
  private RequestMapper() {
  }

  /**
   * Build StatusRequest by developerId and productId.
   *
   * @param developerId the developer id
   * @param productId the product id
   * @return the status request
   */
  public static StatusRequest build(String developerId, String productId) {
    StatusRequest statusRequest = new StatusRequest();

    statusRequest.setDeveloperId(developerId);
    statusRequest.setProductId(productId);
    statusRequest.setStatus(RequestStatus.UNVIEW);

    return statusRequest;
  }

  /**
   * Convert StatusRequest to StatusRequestView.
   *
   * @param statusRequest the status request
   * @return the status request view
   */
  public static StatusRequestView toModel(StatusRequest statusRequest) {
    // TODO: 17/7/19

    return null;
  }
}
