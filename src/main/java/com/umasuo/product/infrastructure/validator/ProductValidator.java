package com.umasuo.product.infrastructure.validator;

import com.umasuo.exception.ParametersException;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.enums.ProductStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于判断Product相关的信息是否合法.
 */
public final class ProductValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductValidator.class);

  /**
   * Private constructor.
   */
  private ProductValidator() {
  }

  /**
   * Check status.
   *
   * @param product the product
   */
  public static void checkStatus(Product product) {
    if (product.getStatus().equals(ProductStatus.PUBLISHED) ||
        product.getStatus().equals(ProductStatus.CHECKING)) {
      LOG.debug("Can not delete or update a published product in status: {}.",
          product.getStatus());
      throw new ParametersException(
          "Can not delete or update a published product when it is checking or published");
    }
  }

  /**
   * Check developer.
   *
   * @param developerId the developer id
   * @param product the product
   */
  public static void checkDeveloper(String developerId, Product product) {
    if (!product.getDeveloperId().equals(developerId)) {
      LOG.debug("Product: {} not belong to developer: {}.", product.getId(), developerId);
      throw new ParametersException("The Product not belong to the developer: " + developerId + "," +
          " productId: " + product.getId());
    }
  }

  /**
   * Validate status.
   *
   * @param requestStatus the request status
   * @param realStatus the real status
   */
  public static void validateStatus(ProductStatus requestStatus, ProductStatus realStatus) {
    LOG.debug("Enter. requestStatus: {}, realStatus: {}.", requestStatus, realStatus);

    if (!requestStatus.equals(realStatus)) {
      LOG.debug("Product status not match, requestStatus: {}, realStatus: {}.",
          requestStatus, realStatus);
      throw new ParametersException("Product status is wrong");
    }

    LOG.debug("Exit.");
  }
}
